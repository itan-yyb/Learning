package com.itan.producer.three;

import com.itan.utils.RabbitMQUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

/**
 * @Author: ye.yanbin
 * @Date: 2022/3/28
 * 死信队列 --- 消息生产者实现
 */
public class DeadProducer {
    private static final String NORMAL_EXCHANGE = "normal_exchange";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        //获取连接
        Connection connection = RabbitMQUtils.getConnection();
        //从连接中创建通道
        Channel channel = connection.createChannel();
        //设置消息属性--->设置过期时间（单位ms）
        // AMQP.BasicProperties props = new AMQP.BasicProperties()
        //                             .builder().expiration("10000").build();
        //发送10条消息
        for (int i = 1; i <= 10; i++) {
            //消息内容
            String msg = i + "_" + UUID.randomUUID().toString().substring(0, 10);
            /**
             * 发送消息
             * 参数1：交换机名称，如果直接发送信息到队列，则交换机名称为""
             * 参数2：目标队列名称，表示要将消息发送到那个队列
             * 参数3：设置当前消息的属性
             * 参数4：消息的内容
             */
            channel.basicPublish(NORMAL_EXCHANGE,"a", null, msg.getBytes());
            // Thread.sleep(2000);
        }
        //关闭通道和连接
        channel.close();
        connection.close();
    }
}
