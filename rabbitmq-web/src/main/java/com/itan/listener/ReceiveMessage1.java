package com.itan.listener;

import com.itan.entity.Order;
import com.itan.entity.User;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Author: ye.yanbin
 * @Date: 2022/4/5
 * @RabbitListener+@RabbitHandler配合使用：接收同一个队列不同类型的消息
 */
@Slf4j
@Component
@RabbitListener(queues = {"queue2","queue3","queue4"})
public class ReceiveMessage1 {
    // @RabbitHandler
    // public void receive(Order order) {
    //     log.info("接收到queue4中Order消息：[{}]", order);
    // }
    //
    // @RabbitHandler
    // public void receive(User user) {
    //     log.info("接收到queue4中User消息：[{}]", user);
    // }

    @RabbitHandler
    public void receive(Message message, Channel channel, Order order) {
        log.info("接收到queue2中Order消息：[{}]", order);
        //消息头属性
        MessageProperties properties = message.getMessageProperties();
        //获取消息的标签，在channel内是按顺序递增的
        long deliveryTag = properties.getDeliveryTag();
        try {
            /**
             * 成功消费之后进行手动应答，RabbitMQ就可以将消费过的消息丢弃了
             * 参数1：消息的标记（tag）
             * 参数2：是否批量应答；false表示消费一个才应答一个，true表示消费一个之后将channel中小于tag标记的消息都应答了
             */
            channel.basicAck(deliveryTag, false);
        } catch (IOException e) {
            log.info("网络中断...");
            e.printStackTrace();
        }
    }
}
