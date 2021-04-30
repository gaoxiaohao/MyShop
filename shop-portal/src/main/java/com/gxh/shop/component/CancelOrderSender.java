package com.gxh.shop.component;

import com.gxh.shop.bo.RabbitEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author gxh
 */
@Slf4j
@Component
public class CancelOrderSender {


    @Autowired
    private RabbitTemplate amqpTemplate;



    public void sendMessage(Long orderId,final long delayTimes){
        amqpTemplate.convertAndSend(RabbitEnum.ORDER_TTL_MASSAGE.getExchange(), RabbitEnum.ORDER_TTL_MASSAGE.getRoutKey(), orderId, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                //给消息设置延迟毫秒值
                message.getMessageProperties().setExpiration(String.valueOf(delayTimes));
                log.info("111111111");
                return message;
            }
        });

    }
}
