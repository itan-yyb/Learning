package com.itan.controller;

import com.alibaba.fastjson.JSON;
import com.itan.entity.Order;
import com.itan.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;

/**
 * @Author: ye.yanbin
 * @Date: 2022/3/29
 */
@Slf4j
@RestController
@RequestMapping("/send")
public class SendMessageCotroller {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("message/{message}")
    public void sendMessage(@PathVariable String message) {
        log.info("发送消息到MQ - 入参 [{}]", JSON.toJSONString(message));
        rabbitTemplate.convertAndSend("normal_exchange", "a", ("来自normal_queue的死信消息：" + message).getBytes());
        log.info("发送消息到MQ - 完成");
    }

    @GetMapping("message/{message}/{ttlTime}")
    public void sendMessageTTL(@PathVariable String message, @PathVariable String ttlTime) {
        log.info("发送带有过期时间的消息到MQ - 消息内容 [{}] - 存活时间 [{}]", JSON.toJSONString(message), JSON.toJSONString(ttlTime));
        rabbitTemplate.convertAndSend("normal_exchange", "a", message.getBytes(), correlationData -> {
            correlationData.getMessageProperties().setExpiration(ttlTime);
            return correlationData;
        });
        log.info("发送带有过期时间的消息到MQ - 完成");
    }

    @GetMapping("/message")
    public void sendMessage() {
        for (int i = 1; i < 5; i++) {
            if (i % 2 ==0) {
                Order order = Order.builder().id(i)
                        .orderNo(UUID.randomUUID().toString())
                        .price(15000f).remark("创建订单").build();
                rabbitTemplate.convertAndSend("queue1", order);
            } else {
                User user = User.builder().id(i).userName(UUID.randomUUID().toString())
                        .age(20 + i).build();
                rabbitTemplate.convertAndSend("queue1", user);
            }
        }
    }
}
