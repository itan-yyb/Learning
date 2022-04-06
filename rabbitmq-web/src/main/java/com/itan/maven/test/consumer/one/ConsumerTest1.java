package com.itan.maven.test.consumer.one;

import com.itan.utils.RabbitMQUtils;
import com.rabbitmq.client.*;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author: ye.yanbin
 * @Date: 2022/3/24
 * 消息消费者 --- 简单模式
 */
public class ConsumerTest1 {
    public static void main(String[] args) throws IOException, TimeoutException {
        //1、获取连接
        Connection connection = RabbitMQUtils.getConnection();
        //2、从连接中创建通道
        Channel channel = connection.createChannel();
        /**
         * 3、DefaultConsumer类实现了Consumer接口，通过传入一个通道，
         * 告诉服务器我们需要那个通道的消息，如果通道中有消息，就会执行回调函数handleDelivery
         */
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                //body就是从消息队列中获取的数据
                String msg = new String(body);
                System.out.println("消费消息：" + msg);
            }
        };
        /**
         * 消费者消费消息
         * 参数1：队列名称
         * 参数2：消息成功消费之后是否需要自动应答，true代表自动应答，false代表手动应答（自动手动应答对应消息确认机制）
         * 参数3：消息消费者
         */
        //4、自动回复队列应答 -- RabbitMQ中的消息确认机制
        channel.basicConsume("queue1",true, consumer);
    }
}
