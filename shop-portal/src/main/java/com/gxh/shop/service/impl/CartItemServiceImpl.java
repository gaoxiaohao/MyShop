package com.gxh.shop.service.impl;

import com.gxh.shop.model.OCartItem;
import com.gxh.shop.bo.CartProduct;
import com.gxh.shop.bo.CartPromotionItem;
import com.gxh.shop.service.CartItemService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author gxh
 */
@Service
public class CartItemServiceImpl implements CartItemService {
    @Override
    public int add(OCartItem cartItem) {
        return 0;
    }

    @Override
    public List<OCartItem> list(Long memberId) {
        return null;
    }

    @Override
    public List<CartPromotionItem> listPromotion(Long memberId, List<Long> cartIds) {
        return null;
    }

    @Override
    public int updateQuantity(Long id, Long memberId, Integer quantity) {
        return 0;
    }

    @Override
    public int delete(Long memberId, List<Long> ids) {
        return 0;
    }

    @Override
    public CartProduct getCartProduct(Long productId) {
        return null;
    }

    @Override
    public int updateAttr(OCartItem cartItem) {
        return 0;
    }

    @Override
    public int clear(Long memberId) {
        return 0;
    }
}
