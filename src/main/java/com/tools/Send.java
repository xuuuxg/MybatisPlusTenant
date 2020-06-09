package com.tools;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Component
public class Send {

    private final static String QUEUE_NAME = "hello";

    @Autowired
    private RabbitTemplate rabbitTemplate;


    public void send(String msg) {

        rabbitTemplate.convertAndSend(this.QUEUE_NAME,"direct", msg);
    }
}
