package com.example.controller;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ProducerController {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @PostMapping("/sendEmail")
    public String sendEmail(String email, String subject, String text) {
        Map<String, String> mailInfo = new HashMap<>();
        mailInfo.put("from", "zy@ifl.ee");
        mailInfo.put("to", email);
        mailInfo.put("subject", subject);
        mailInfo.put("text", text);
        rabbitTemplate.convertAndSend("sendEmailExchange", "sendEmail", mailInfo);
        System.out.println("msg sent at " + new Date());
        return "email sent";
    }

    @GetMapping("/delayMsg")
    public String delayMsg(String msg) {
        rabbitTemplate.convertAndSend("delayExchange", "delay", msg, message -> {
            message.getMessageProperties().setHeader("x-delay", 25 * 1000);
            return message;
        });
        System.out.println("msg sent at " + new Date());
        return "msg sent";
    }

}
