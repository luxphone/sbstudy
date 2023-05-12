package com.example.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class TestMQConfig {

    @Bean
    public DirectExchange sendEmailExchange() {
        return new DirectExchange("sendEmailExchange", true, false);
    }

    @Bean
    public Queue sendEmailQueue() {
        return new Queue("sendEmailQueue", true);
    }

    @Bean
    public Binding sendEmailBinding() {
        return BindingBuilder.bind(sendEmailQueue()).to(sendEmailExchange()).with("sendEmail");
    }

    @Bean
    public DirectExchange delayExchange() {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-delayed-message", "direct");
        DirectExchange directExchange = new DirectExchange("delayExchange", true, false, arguments);
        directExchange.setDelayed(true);
        return directExchange;
    }

    @Bean
    public Queue delayQueue() {
        return QueueBuilder.durable("delayQueue").deadLetterExchange("delayExecuteExchange").deadLetterRoutingKey("delayExecute").build();
    }

    @Bean
    public Binding delayBinding() {
        return BindingBuilder.bind(delayQueue()).to(delayExchange()).with("delay");
    }

    @Bean
    public DirectExchange delayExecuteExchange() {
        return new DirectExchange("delayExecuteExchange", true, false);
    }

    @Bean
    public Queue delayExecuteQueue() {
        return new Queue("delayExecuteQueue", true);
    }

    @Bean
    public Binding delayExecuteBinding() {
        return BindingBuilder.bind(delayExecuteQueue()).to(delayExecuteExchange()).with("delayExecute");
    }

}
