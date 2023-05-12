package com.example.mqconsumer;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

@Component
public class DelayConsumer {

    @RabbitListener(queues = "delayQueue")
    public void receive(String msg, Message message, Channel channel) throws IOException {
        System.out.println("ConsumerB receive msg at " + new Date() + ", msg: " + msg);
        channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
    }

}
