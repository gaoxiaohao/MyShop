package com.gxh.shop.service;

import com.gxh.shop.bo.CartProduct;
import com.gxh.shop.bo.CartPromotionItem;
import com.gxh.shop.model.OCartItem;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author gxh
 */
public interface CartItemService {

    /**
     * 查询购物车中是否包含该商品，有增加数量，无添加到购物车
     */
    @Transactional
    int add(OCartItem cartItem);

    /**
     * 根据会员编号获取购物车列表
     */
    List<OCartItem> list(Long memberId);

    /**
     * 获取包含促销活动信息的购物车列表
     */
    List<CartPromotionItem> listPromotion(Long memberId, List<Long> cartIds);

    /**
     * 修改某个购物车商品的数量
     */
    int updateQuantity(Long id, Long memberId, Integer quantity);

    /**
     * 批量删除购物车中的商品
     */
    int delete(Long memberId,List<Long> ids);

    /**
     *获取购物车中用于选择商品规格的商品信息
     */
    CartProduct getCartProduct(Long productId);

    /**
     * 修改购物车中商品的规格
     */
    @Transactional
    int updateAttr(OCartItem cartItem);

    /**
     * 清空购物车
     */
    int clear(Long memberId);
}
