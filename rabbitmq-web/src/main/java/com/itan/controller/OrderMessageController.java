package com.itan.controller;

import com.alibaba.fastjson.JSON;
import com.itan.entity.Order;
import com.itan.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;

/**
 * @Author: ye.yanbin
 * @Date: 2022/3/31
 * 订单消息发送
 */
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderMessageController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 使用序列化对象，要求对象必须实现Serializable接口
     */
    @GetMapping("/serializable/{message}")
    public void sendMessage1(@PathVariable String message) {
        log.info("发送消息到MQ - 入参 [{}]", JSON.toJSONString(message));
        Order order = Order.builder().id(1)
                        .orderNo(UUID.randomUUID().toString())
                        .price(15000f).remark(message).build();
        rabbitTemplate.convertAndSend("queue1", order);
        log.info("发送消息到MQ - 完成");
    }

    /**
     * 使用序列化字节数组，二进制字节数组存储
     */
    @GetMapping("/byte/{message}")
    public void sendMessage2(@PathVariable String message) {
        log.info("发送消息到MQ - 入参 [{}]", JSON.toJSONString(message));
        Order order = Order.builder().id(2)
                        .orderNo(UUID.randomUUID().toString())
                        .price(15000f).remark(message).build();
        //序列化数组
        byte[] bytes = SerializationUtils.serialize(order);
        rabbitTemplate.convertAndSend("queue2", bytes);
        log.info("发送消息到MQ - 完成");
    }

    @GetMapping("/json/{message}")
    public void sendMessage3(@PathVariable String message) {
        log.info("发送消息到MQ - 入参 [{}]", JSON.toJSONString(message));
        Order order = Order.builder().id(2)
                        .orderNo(UUID.randomUUID().toString())
                        .price(15000f).remark(message).build();
        rabbitTemplate.convertAndSend("queue3", order);
        log.info("发送消息到MQ - 完成");
    }

    @GetMapping("/message")
    public void sendMessage4() {
        for (int i = 1; i < 5; i++) {
            if (i % 2 == 0) {
                Order order = Order.builder().id(i)
                        .orderNo(UUID.randomUUID().toString())
                        .price(15000f).remark("创建订单").build();
                rabbitTemplate.convertAndSend("queue4", order);
            } else {
                User user = User.builder().id(i).userName(UUID.randomUUID().toString())
                        .age(20 + i).build();
                rabbitTemplate.convertAndSend("queue4", user);
            }
        }
    }
}
