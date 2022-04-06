package com.itan.listener;

import com.itan.entity.Order;
import com.itan.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Author: ye.yanbin
 * @Date: 2022/4/5
 * @RabbitListener+@RabbitHandler配合使用：接收同一个队列不同类型的消息
 */
@Slf4j
@Component
@RabbitListener(queues = {"queue4"})
public class ReceiveMessage1 {
    @RabbitHandler
    public void receive(Order order) {
        log.info("接收到queue4中Order消息：[{}]", order);
    }

    @RabbitHandler
    public void receive(User user) {
        log.info("接收到queue4中User消息：[{}]", user);
    }
}
