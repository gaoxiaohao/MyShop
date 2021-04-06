package com.gxh.shop.bo;


import lombok.Getter;

/**
 * @author gxh
 */
@Getter
public enum RabbitEnum {

    // 订单消息队列信息
    ORDER_MASSAGE("gxh.shop.direct","gxh.shop.queue","gxh.shop.routKey"),
    //  订单延迟队列信息
    ORDER_TTL_MASSAGE("gxh.shop.ttl.direct","gxh.shop.ttl.queue","gxh.shop.ttl.routKey");

    private String exchange;

    private String name;

    private String routKey;

    RabbitEnum(String exchange,String name,String routKey){
        this.exchange = exchange;
        this.name = name;
        this.routKey = routKey;

    }
    
}
