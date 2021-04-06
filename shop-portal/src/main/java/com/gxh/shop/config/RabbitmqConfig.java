package com.gxh.shop.config;

import com.gxh.shop.bo.RabbitEnum;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gxh
 */
@Configuration
public class RabbitmqConfig {
    

    /**
     * 订单交换机
     * */
    @Bean
    public DirectExchange orderExchange(){
        return  (DirectExchange)ExchangeBuilder.directExchange(RabbitEnum.ORDER_MASSAGE.getExchange())
                .durable(true)
                .build();
    }

    /**
     * 延迟交换机
     * */
    @Bean
    public DirectExchange ttlOrderExchange(){
        return  (DirectExchange)ExchangeBuilder.directExchange(RabbitEnum.ORDER_TTL_MASSAGE.getExchange())
                .durable(true)
                .build();
     
    }


    /**
     * 订单实际消费队列
     */
    @Bean
    public Queue orderQueue() {
        return new Queue(RabbitEnum.ORDER_MASSAGE.getName());
    }

    /**
     * 延迟队列
     */
    @Bean
    public Queue ttlOrderQueue(){
        Map<String,Object> map = new HashMap<>(2);
        //过期后转发到的交换机
        map.put("x-dead-letter-exchange",RabbitEnum.ORDER_MASSAGE.getExchange());
        map.put("x-dead-letter-routing-key",RabbitEnum.ORDER_MASSAGE.getRoutKey());
        return QueueBuilder.durable(RabbitEnum.ORDER_TTL_MASSAGE.getName()).withArguments(map).build();
    }

    /**
     * 订单队列绑定到交换机
     */
    @Bean
    Binding binding(@Qualifier("orderExchange") DirectExchange orderDirect,
                    @Qualifier("orderQueue") Queue orderQueue){
        return BindingBuilder
                .bind(orderQueue)
                .to(orderDirect)
                .with(RabbitEnum.ORDER_MASSAGE.getRoutKey());
        
    }

    /**
     * 订单延迟队列绑定到交换机
     */
    @Bean
    Binding bindingDead(@Qualifier("ttlOrderExchange") DirectExchange orderDirect,
                    @Qualifier("ttlOrderQueue") Queue orderQueue){
        return BindingBuilder
                .bind(orderQueue)
                .to(orderDirect)
                .with(RabbitEnum.ORDER_TTL_MASSAGE.getRoutKey());

    }
    

}
