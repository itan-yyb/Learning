package com.itan.maven.test.consumer.three;

import com.itan.utils.RabbitMQUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author: ye.yanbin
 * @Date: 2022/3/28
 * 死信队列 --- 消息消费者实现
 */
public class DeadConsumer {
    private static final String DEAD_QUEUE = "dead_queue";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        System.out.println("死信队列消费者等待消费......");
        //获取连接
        Connection connection = RabbitMQUtils.getConnection();
        //从连接中创建通道
        Channel channel = connection.createChannel();
        /**
         * 接收消息时的回调
         * 参数 consumerTag：与消费者关联的消费者标签
         * 参数 message：传递的消息
         */
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            //从消息队列中获取的数据
            byte[] body = message.getBody();
            String msg = new String(body);
            System.out.println("绑定键为：" + message.getEnvelope().getRoutingKey() + " 消息内容为：" + msg);
        };
        /**
         * 消费者取消消费时的回调，若消息正常消费，则不触发此回调
         * 参数 consumerTag：与消费者关联的消费者标签
         */
        CancelCallback cancelCallback = consumerTag -> {
            System.out.println("消息消费被取消：" + consumerTag);
        };
        /**
         * 消费者消费消息
         * 参数1：队列名称
         * 参数2：消息成功消费之后是否需要自动应答，true代表自动应答，false代表手动应答（自动手动应答对应消息确认机制）
         * 参数3：接收消息时的回调
         * 参数4：消费者取消消费时的回调
         */
        channel.basicConsume(DEAD_QUEUE, true, deliverCallback, cancelCallback);
    }
}


















