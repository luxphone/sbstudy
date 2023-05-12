package com.example.mqconsumer;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

@Component
public class SendEmailConsumer {

    @Autowired
    JavaMailSender mailSender;

    @RabbitListener(queues = "sendEmailQueue")
    public void receive(Map<String, String> mailInfo, Message message, Channel channel) throws IOException {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom(mailInfo.get("from"));
        mail.setTo(mailInfo.get("to"));
        mail.setSubject(mailInfo.get("subject"));
        mail.setText(mailInfo.get("text"));
        mailSender.send(mail);
        System.out.println("ConsumerA receive msg " + " at " + new Date() + ", sending email...");
        channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
    }

}
