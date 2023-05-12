package com.example.config;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig implements InitializingBean {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Override
    public void afterPropertiesSet() throws Exception {

        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            System.out.println("confirm info below");
            System.out.println("correlationData: " + correlationData);
            System.out.println("ack: " + ack);
            System.out.println("cause " + cause);
        });

        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            System.out.println("message: " + message);
            System.out.println("replyCode: " + replyCode);
            System.out.println("replyText: " + replyText);
            System.out.println("exchange: " + exchange);
            System.out.println("routingKey: " + routingKey);
        });
        rabbitTemplate.setMandatory(true);
    }

}

