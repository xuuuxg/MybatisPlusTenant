package com.tools;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@Component
@RabbitListener(
        bindings = @QueueBinding(
        value = @Queue("DirectQueue"),
        exchange = @Exchange(value = "hello", type = ExchangeTypes.DIRECT),
        key = "direct"
))
public class RabbitOnMessage {

    @RabbitHandler
    public void onMessage(@Payload String msg) {
        System.out.println(msg);
    }

}
