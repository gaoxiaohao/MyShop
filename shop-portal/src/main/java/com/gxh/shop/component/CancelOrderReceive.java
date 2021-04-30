package com.gxh.shop.component;

import com.gxh.shop.service.PortalOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author gxh
 */
@Slf4j
@Component
@RabbitListener(queues = "gxh.shop.queue")
public class CancelOrderReceive {
    @Autowired
    private PortalOrderService portalOrderService;

    @RabbitHandler
    public void receive(Long orderId) {
        portalOrderService.cancelOrder(orderId);
        log.info("当前id{}", orderId);
        
    }
}
