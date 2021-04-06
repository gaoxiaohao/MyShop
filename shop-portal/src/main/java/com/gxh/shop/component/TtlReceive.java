package com.gxh.shop.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


/**
 * @author gxh
 */
@Slf4j
@Component
@RabbitListener(queues = "gxh.shop.queue")
public class TtlReceive {


    @RabbitHandler
    public void receive(Long orderId) {

        log.info("当前id{}", orderId);
        
    }
}
