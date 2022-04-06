package com.itan;

import com.alibaba.fastjson.JSON;
import com.itan.entity.Order;
import com.itan.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.amqp.utils.SerializationUtils;
import java.util.UUID;

@SpringBootTest
class RabbitmqApplicationTests {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 使用序列化对象，要求对象必须实现Serializable接口
     */
    @Test
    public void test1() {
        Order order = Order.builder().id(1)
                        .orderNo(UUID.randomUUID().toString())
                        .price(15000f).remark("创建订单").build();
        rabbitTemplate.convertAndSend("queue1", order);
    }

    /**
     * 使用序列化字节数组
     */
    @Test
    public void test2() {
        Order order = Order.builder().id(1)
                .orderNo(UUID.randomUUID().toString())
                .price(15000f).remark("创建订单").build();
        //对象序列化
        byte[] bytes = SerializationUtils.serialize(order);
        rabbitTemplate.convertAndSend("queue2", bytes);
    }

    /**
     * 使用JSON字符串
     */
    @Test
    public void test3() {
        Order order = Order.builder().id(1)
                .orderNo(UUID.randomUUID().toString())
                .price(15000f).remark("创建订单").build();
        rabbitTemplate.convertAndSend("queue3", order);
    }

    @Test
    public void test4() {
        for (int i = 1; i < 5; i++) {
            if (i % 2 ==0) {
                Order order = Order.builder().id(i)
                        .orderNo(UUID.randomUUID().toString())
                        .price(15000f).remark("创建订单").build();
                rabbitTemplate.convertAndSend("queue1", JSON.toJSONString(order));
            } else {
                User user = User.builder().id(i).userName(UUID.randomUUID().toString())
                        .age(20 + i).build();
                rabbitTemplate.convertAndSend("queue1", JSON.toJSONString(user));
            }
        }
    }
}
