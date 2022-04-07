package com.itan.listener;

import com.alibaba.fastjson.JSON;
import com.itan.entity.Order;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.stereotype.Component;


/**
 * @Author: ye.yanbin
 * @Date: 2022/3/29
 * 消费MQ消息
 */
@Slf4j
@Component
public class ReceiveMessage {

    /**
     * queues：声明需要监听的所有队列，多个队列用逗号隔开
     * msg：原生消息详细信息，包含消息（头+体）
     * channel：当前传输数据的通道
     */
    @RabbitListener(queues = "dead_queue")
    public void receive1(Message msg, Channel channel) {
        log.info("收到dead_queue队列的消息：[{}]", new String(msg.getBody()));
    }

    /**
     * T<发送消息的类型> t：可以直接写发送消息的类型，好处是不用手动转换消息的类型
     * 比如：直接使用Order order对象接收消息
     */
    // @RabbitListener(queues = {"queue1"})
    public void receive1(Order order) {
        log.info("接收到queue1队列的消息：[{}]", order);
    }

    // @RabbitListener(queues = "queue2")
    public void receive2(byte[] bytes) {
        //接收byte类型的消息，并反序列化
        Order order = (Order) SerializationUtils.deserialize(bytes);
        log.info("接收到queue2队列的消息：[{}]", order);
    }

    // @RabbitListener(queues = "queue3")
    public void receive3(Order order) {
        log.info("接收到queue3队列的消息：[{}]", order);
    }
}
