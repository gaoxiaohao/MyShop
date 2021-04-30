package com.gxh.shop.bo;

import com.gxh.shop.model.SMemberAddress;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author gxh
 */
@Getter
@Setter
public class ConfirmOrderResult {

    //包含优惠信息的购物车信息
    private List<CartPromotionItem> cartPromotionItemList;
    //用户收货地址列表
    private List<SMemberAddress> memberAddresses;
    
    //会员持有的积分
    private Integer memberIntegration;
    //计算的金额
    private CalcAmount calcAmount;

    public static class CalcAmount{
        //订单商品总金额
        private BigDecimal totalAmount;
        //运费
        private BigDecimal freightAmount;
        //活动优惠
        private BigDecimal promotionAmount;
        //应付金额
        private BigDecimal payAmount;

        public BigDecimal getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(BigDecimal totalAmount) {
            this.totalAmount = totalAmount;
        }

        public BigDecimal getFreightAmount() {
            return freightAmount;
        }

        public void setFreightAmount(BigDecimal freightAmount) {
            this.freightAmount = freightAmount;
        }

        public BigDecimal getPromotionAmount() {
            return promotionAmount;
        }

        public void setPromotionAmount(BigDecimal promotionAmount) {
            this.promotionAmount = promotionAmount;
        }

        public BigDecimal getPayAmount() {
            return payAmount;
        }

        public void setPayAmount(BigDecimal payAmount) {
            this.payAmount = payAmount;
        }
    }
}
