package com.gxh.shop.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.github.pagehelper.PageHelper;
import com.gxh.shop.api.CommonPage;
import com.gxh.shop.bo.CartPromotionItem;
import com.gxh.shop.bo.ConfirmOrderResult;
import com.gxh.shop.bo.OrderDetails;
import com.gxh.shop.bo.OrderParam;
import com.gxh.shop.component.CancelOrderSender;
import com.gxh.shop.dao.PortalOrderDao;
import com.gxh.shop.dao.PortalOrderItemDao;
import com.gxh.shop.mapper.OOrderItemMapper;
import com.gxh.shop.mapper.OOrderMapper;
import com.gxh.shop.mapper.OOrderSettingMapper;
import com.gxh.shop.mapper.PSkuStockMapper;
import com.gxh.shop.model.*;
import com.gxh.shop.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author gxh
 */
@Service
public class PortalOrderServiceImpl implements PortalOrderService {

    @Autowired
    private MemberService memberService;
    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private PSkuStockMapper skuStockMapper;
    @Autowired
    private OOrderMapper orderMapper;
    @Autowired
    private OOrderSettingMapper orderSettingMapper;
    @Autowired
    private OOrderItemMapper orderItemMapper;
    @Autowired
    private CancelOrderSender cancelOrderSender;
    @Autowired
    private MemberAddressService memberAddressService;
    @Autowired
    private RedisService redisService;

    @Autowired
    private PortalOrderItemDao orderItemDao;
    @Autowired
    private PortalOrderDao portalOrderDao;

    @Value("${redis.key.orderId}")
    private String REDIS_KEY_ORDER_ID;
    @Value("${redis.database}")
    private String REDIS_DATABASE;
    

    @Override
    public ConfirmOrderResult generateConfirmOrder(List<Long> cartIds) {
        ConfirmOrderResult result = new ConfirmOrderResult();
        //?????????????????????
        SMember currentMember = memberService.getCurrentMember();
        List<CartPromotionItem> cartPromotionItemList = cartItemService.listPromotion(currentMember.getId(),cartIds);
        result.setCartPromotionItemList(cartPromotionItemList);
        //??????????????????????????????
        List<SMemberAddress> memberReceiveAddressList = memberAddressService.list();
        result.setMemberAddresses(memberReceiveAddressList);
        //?????????????????????????????????????????????
        ConfirmOrderResult.CalcAmount calcAmount = calcCartAmount(cartPromotionItemList);
        result.setCalcAmount(calcAmount);
        return result;
    }

    /**
     * ?????????????????????????????????
     */
    private ConfirmOrderResult.CalcAmount calcCartAmount(List<CartPromotionItem> cartPromotionItemList) {
        ConfirmOrderResult.CalcAmount calcAmount = new ConfirmOrderResult.CalcAmount();
        calcAmount.setFreightAmount(new BigDecimal(0));
        BigDecimal totalAmount = new BigDecimal("0");
        BigDecimal promotionAmount = new BigDecimal("0");
        for (CartPromotionItem cartPromotionItem : cartPromotionItemList) {
            totalAmount = totalAmount.add(cartPromotionItem.getPrice().multiply(new BigDecimal(cartPromotionItem.getQuantity())));
            promotionAmount = promotionAmount.add(cartPromotionItem.getReduceAmount().multiply(new BigDecimal(cartPromotionItem.getQuantity())));
        }
        calcAmount.setTotalAmount(totalAmount);
        calcAmount.setPromotionAmount(promotionAmount);
        calcAmount.setPayAmount(totalAmount.subtract(promotionAmount));
        return calcAmount;
    }
    @Override
    public Map<String, Object> generateOrder(OrderParam orderParam) {
        List<OOrderItem> orderItemList = new ArrayList<>();
        //??????????????????????????????
        SMember currentMember = memberService.getCurrentMember();
        List<CartPromotionItem> cartPromotionItemList = cartItemService.listPromotion(currentMember.getId(), orderParam.getCartIds());
        for (CartPromotionItem cartPromotionItem : cartPromotionItemList) {
            //????????????????????????
            OOrderItem orderItem = new OOrderItem();
            orderItem.setProductId(cartPromotionItem.getProductId());
            orderItem.setProductName(cartPromotionItem.getProductName());
            orderItem.setProductPic(cartPromotionItem.getProductPic());
            orderItem.setProductAttr(cartPromotionItem.getProductAttr());
            orderItem.setProductBrand(cartPromotionItem.getProductBrand());
            orderItem.setProductSn(cartPromotionItem.getProductSn());
            orderItem.setProductPrice(cartPromotionItem.getPrice());
            orderItem.setProductQuantity(cartPromotionItem.getQuantity());
            orderItem.setProductSkuId(cartPromotionItem.getProductSkuId());
            orderItem.setProductSkuCode(cartPromotionItem.getProductSkuCode());
            orderItem.setProductCategoryId(cartPromotionItem.getProductCategoryId());
            orderItem.setPromotionAmount(cartPromotionItem.getReduceAmount());
            orderItem.setPromotionName(cartPromotionItem.getPromotionMessage());
            orderItem.setGiftIntegration(cartPromotionItem.getIntegration());
            orderItem.setGiftGrowth(cartPromotionItem.getGrowth());
            orderItemList.add(orderItem);
        }
        //??????????????????????????????????????????
        if (!hasStock(cartPromotionItemList)) {
            //log.fail("???????????????????????????");
        }
        //??????order_item???????????????
        handleRealAmount(orderItemList);
        //??????????????????
        lockStock(cartPromotionItemList);
        //?????????????????????????????????????????????????????????????????????????????????
        OOrder order = new OOrder();
        order.setDiscountAmount(new BigDecimal(0));
        order.setTotalAmount(calcTotalAmount(orderItemList));
        order.setFreightAmount(new BigDecimal(0));
        order.setPayAmount(calcPayAmount(order));
        //???????????????????????????????????????
        order.setMemberId(currentMember.getId());
        order.setCreateTime(new Date());
        order.setMemberUsername(currentMember.getUsername());
        //???????????????0->????????????1->????????????2->??????
        order.setPayType(orderParam.getPayType());
        //???????????????0->PC?????????1->app??????
        order.setSourceType(1);
        //???????????????0->????????????1->????????????2->????????????3->????????????4->????????????5->????????????
        order.setStatus(0);
        //???????????????0->???????????????1->????????????
        order.setOrderType(0);
        //???????????????????????????????????????????????????
        SMemberAddress address = memberAddressService.getItem(orderParam.getMemberReceiveAddressId());
        order.setReceiverName(address.getName());
        order.setReceiverPhone(address.getPhoneNumber());
        order.setReceiverPostCode(address.getPostCode());
        order.setReceiverProvince(address.getProvince());
        order.setReceiverCity(address.getCity());
        order.setReceiverRegion(address.getRegion());
        order.setReceiverDetailAddress(address.getDetailAddress());
        //0->????????????1->?????????
        order.setConfirmStatus(0);
        order.setDeleteStatus(0);
        //???????????????
        order.setOrderSn(generateOrderSn(order));
        //????????????????????????
        List<OOrderSetting> orderSettings = orderSettingMapper.selectByExample(new OOrderSettingExample());
        if(CollUtil.isNotEmpty(orderSettings)){
            order.setAutoConfirmDay(orderSettings.get(0).getConfirmOvertime());
        }
        // TODO: 2018/9/3 bill_*,delivery_*
        //??????order??????order_item???
        orderMapper.insert(order);
        for (OOrderItem orderItem : orderItemList) {
            orderItem.setOrderId(order.getId());
            orderItem.setOrderSn(order.getOrderSn());
        }
        orderItemDao.insertList(orderItemList);
        //?????????????????????????????????
        deleteCartItemList(cartPromotionItemList, currentMember);
        //??????????????????????????????
        sendDelayMessageCancelOrder(order.getId());
        Map<String, Object> result = new HashMap<>();
        result.put("order", order);
        result.put("orderItemList", orderItemList);
        return result;
    }

    /**
     * ????????????????????????????????????
     */
    private void deleteCartItemList(List<CartPromotionItem> cartPromotionItemList, SMember currentMember) {
        List<Long> ids = new ArrayList<>();
        for (CartPromotionItem cartPromotionItem : cartPromotionItemList) {
            ids.add(cartPromotionItem.getId());
        }
        cartItemService.delete(currentMember.getId(), ids);
    }
    /**
     * ??????18???????????????:8?????????+2???????????????+2???????????????+6???????????????id
     */
    private String generateOrderSn(OOrder order) {
        StringBuilder sb = new StringBuilder();
        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String key = REDIS_DATABASE+":"+ REDIS_KEY_ORDER_ID + date;
        Long increment = redisService.incr(key, 1);
        sb.append(date);
        sb.append(String.format("%02d", order.getSourceType()));
        sb.append(String.format("%02d", order.getPayType()));
        String incrementStr = increment.toString();
        if (incrementStr.length() <= 6) {
            sb.append(String.format("%06d", increment));
        } else {
            sb.append(incrementStr);
        }
        return sb.toString();
    }

    private void handleRealAmount(List<OOrderItem> orderItemList) {
        for (OOrderItem orderItem : orderItemList) {
            //??????-????????????-???????????????-????????????
            BigDecimal realAmount = orderItem.getProductPrice()
                    .subtract(orderItem.getPromotionAmount())
                    .subtract(orderItem.getCouponAmount())
                    .subtract(orderItem.getIntegrationAmount());
            orderItem.setRealAmount(realAmount);
        }
    }

    /**
     * ?????????????????????????????????
     */
    private void lockStock(List<CartPromotionItem> cartPromotionItemList) {
        for (CartPromotionItem cartPromotionItem : cartPromotionItemList) {
            PSkuStock skuStock = skuStockMapper.selectByPrimaryKey(cartPromotionItem.getProductSkuId());
            skuStock.setLockStock(skuStock.getLockStock() + cartPromotionItem.getQuantity());
            skuStockMapper.updateByPrimaryKeySelective(skuStock);
        }
    }
    /**
     * ????????????????????????
     */
    private BigDecimal calcPayAmount(OOrder order) {
        //?????????+??????-????????????-???????????????-????????????
        BigDecimal payAmount = order.getTotalAmount()
                .add(order.getFreightAmount())
                .subtract(order.getPromotionAmount())
                .subtract(order.getCouponAmount())
                .subtract(order.getIntegrationAmount());
        return payAmount;
    }

    /**
     * ???????????????
     */
    private BigDecimal calcTotalAmount(List<OOrderItem> orderItemList) {
        BigDecimal totalAmount = new BigDecimal("0");
        for (OOrderItem item : orderItemList) {
            totalAmount = totalAmount.add(item.getProductPrice().multiply(new BigDecimal(item.getProductQuantity())));
        }
        return totalAmount;
    }
    /**
     * ????????????????????????????????????
     */
    private boolean hasStock(List<CartPromotionItem> cartPromotionItemList) {
        for (CartPromotionItem cartPromotionItem : cartPromotionItemList) {
            if (cartPromotionItem.getRealStock()==null||cartPromotionItem.getRealStock() <= 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Integer paySuccess(Long orderId, Integer payType) {
        //????????????????????????
        OOrder order = new OOrder();
        order.setId(orderId);
        order.setStatus(1);
        order.setPaymentTime(new Date());
        order.setPayType(payType);
        orderMapper.updateByPrimaryKeySelective(order);
        //????????????????????????????????????????????????????????????
        OrderDetails orderDetail = portalOrderDao.getDetail(orderId);
        int count = portalOrderDao.updateSkuStock(orderDetail.getOrderItemList());
        return count;
    }

    @Override
    public Integer cancelTimeOutOrder() {
        Integer count=0;
        OOrderSetting orderSetting = orderSettingMapper.selectByPrimaryKey(1L);
        //????????????????????????????????????????????????
        List<OrderDetails> timeOutOrders = portalOrderDao.getTimeOutOrders(orderSetting.getNormalOrderOvertime());
        if (CollectionUtils.isEmpty(timeOutOrders)) {
            return count;
        }
        //?????????????????????????????????
        List<Long> ids = new ArrayList<>();
        for (OrderDetails timeOutOrder : timeOutOrders) {
            ids.add(timeOutOrder.getId());
        }
        portalOrderDao.updateOrderStatus(ids, 4);
        for (OrderDetails timeOutOrder : timeOutOrders) {
            //??????????????????????????????
            portalOrderDao.releaseSkuStockLock(timeOutOrder.getOrderItemList());
           
            //??????????????????
            if (timeOutOrder.getUseIntegration() != null) {
                SMember member = memberService.getById(timeOutOrder.getMemberId());
                memberService.updateIntegration(timeOutOrder.getMemberId(), member.getIntegration() + timeOutOrder.getUseIntegration());
            }
        }
        return timeOutOrders.size();
    }

    @Override
    public void cancelOrder(Long orderId) {
        //??????????????????????????????
        OOrderExample example = new OOrderExample();
        example.createCriteria().andIdEqualTo(orderId).andStatusEqualTo(0).andDeleteStatusEqualTo(0);
        List<OOrder> cancelOrderList = orderMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(cancelOrderList)) {
            return;
        }
        OOrder cancelOrder = cancelOrderList.get(0);
        if (cancelOrder != null) {
            //???????????????????????????
            cancelOrder.setStatus(4);
            orderMapper.updateByPrimaryKeySelective(cancelOrder);
            OOrderItemExample orderItemExample = new OOrderItemExample();
            orderItemExample.createCriteria().andOrderIdEqualTo(orderId);
            List<OOrderItem> orderItemList = orderItemMapper.selectByExample(orderItemExample);
            //??????????????????????????????
            if (!CollectionUtils.isEmpty(orderItemList)) {
                portalOrderDao.releaseSkuStockLock(orderItemList);
            }
            //??????????????????
            if (cancelOrder.getUseIntegration() != null) {
                SMember member = memberService.getById(cancelOrder.getMemberId());
                memberService.updateIntegration(cancelOrder.getMemberId(), member.getIntegration() + cancelOrder.getUseIntegration());
            }
        }
    }

    @Override
    public void sendDelayMessageCancelOrder(Long orderId) {
        //????????????????????????
        OOrderSetting orderSetting = orderSettingMapper.selectByPrimaryKey(1L);
        long delayTimes = orderSetting.getNormalOrderOvertime() * 60 * 1000;
        //??????????????????
        cancelOrderSender.sendMessage(orderId, delayTimes);
    }

    @Override
    public void confirmReceiveOrder(Long orderId) {
        SMember member = memberService.getCurrentMember();
        OOrder order = orderMapper.selectByPrimaryKey(orderId);
        if(!member.getId().equals(order.getMemberId())){
            ////Asserts.fail("???????????????????????????");
        }
        if(order.getStatus()!=2){
            //Asserts.fail("????????????????????????");
        }
        order.setStatus(3);
        order.setConfirmStatus(1);
        order.setReceiveTime(new Date());
        orderMapper.updateByPrimaryKey(order);
    }

    @Override
    public CommonPage<OrderDetails> list(Integer status, Integer pageNum, Integer pageSize) {
        if(status==-1){
            status = null;
        }
        SMember member = memberService.getCurrentMember();
        PageHelper.startPage(pageNum,pageSize);
        OOrderExample orderExample = new OOrderExample();
        OOrderExample.Criteria criteria = orderExample.createCriteria();
        criteria.andDeleteStatusEqualTo(0)
                .andMemberIdEqualTo(member.getId());
        if(status!=null){
            criteria.andStatusEqualTo(status);
        }
        orderExample.setOrderByClause("create_time desc");
        List<OOrder> orderList = orderMapper.selectByExample(orderExample);
        CommonPage<OOrder> orderPage = CommonPage.restPage(orderList);
        //??????????????????
        CommonPage<OrderDetails> resultPage = new CommonPage<>();
        resultPage.setPageNum(orderPage.getPageNum());
        resultPage.setPageSize(orderPage.getPageSize());
        resultPage.setTotal(orderPage.getTotal());
        resultPage.setTotalPage(orderPage.getTotalPage());
        if(CollUtil.isEmpty(orderList)){
            return resultPage;
        }
        //??????????????????
        List<Long> orderIds = orderList.stream().map(OOrder::getId).collect(Collectors.toList());
        OOrderItemExample orderItemExample = new OOrderItemExample();
        orderItemExample.createCriteria().andOrderIdIn(orderIds);
        List<OOrderItem> orderItemList = orderItemMapper.selectByExample(orderItemExample);
        List<OrderDetails> orderDetailList = new ArrayList<>();
        for (OOrder omsOrder : orderList) {
            OrderDetails orderDetail = new OrderDetails();
            BeanUtil.copyProperties(omsOrder,orderDetail);
            List<OOrderItem> relatedItemList = orderItemList.stream().filter(item -> item.getOrderId().equals(orderDetail.getId())).collect(Collectors.toList());
            orderDetail.setOrderItemList(relatedItemList);
            orderDetailList.add(orderDetail);
        }
        resultPage.setList(orderDetailList);
        return resultPage;
    }

    @Override
    public OrderDetails detail(Long orderId) {
        OOrder omsOrder = orderMapper.selectByPrimaryKey(orderId);
        OOrderItemExample example = new OOrderItemExample();
        example.createCriteria().andOrderIdEqualTo(orderId);
        List<OOrderItem> orderItemList = orderItemMapper.selectByExample(example);
        OrderDetails orderDetail = new OrderDetails();
        BeanUtil.copyProperties(omsOrder,orderDetail);
        orderDetail.setOrderItemList(orderItemList);
        return orderDetail;
    }

    @Override
    public void deleteOrder(Long orderId) {
        SMember member = memberService.getCurrentMember();
        OOrder order = orderMapper.selectByPrimaryKey(orderId);
        if(!member.getId().equals(order.getMemberId())){
            //Asserts.fail("???????????????????????????");
        }
        if(order.getStatus()==3||order.getStatus()==4){
            order.setDeleteStatus(1);
            orderMapper.updateByPrimaryKey(order);
        }else{
            //Asserts.fail("?????????????????????????????????????????????");
        }
    }
}
