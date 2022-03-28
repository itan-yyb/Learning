package com.itan.consumer.one;

import com.itan.utils.RabbitMQUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author: ye.yanbin
 * @Date: 2022/3/26
 * 消息消费者 --- 消息手动应答
 */
public class ConsumerTest5 {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        System.out.println("消费者2等待消费时间较长......");
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
            try {
                Thread.sleep(10 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("消费消息：" + msg);
            /**
             * 成功消费之后进行手动应答，RabbitMQ就可以将消费过的消息丢弃了
             * 参数1：消息的标记（tag）
             * 参数2：是否批量应答；false表示消费一个才应答一个，true表示消费一个之后将channel中小于tag标记的消息都应答了
             */
            channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
        };
        /**
         * 消费者取消消费时的回调，若消息正常消费，则不触发此回调
         * 参数 consumerTag：与消费者关联的消费者标签
         */
        CancelCallback cancelCallback = consumerTag -> {
            System.out.println("消息消费被取消：" + consumerTag);
        };
        //设置预取值
        channel.basicQos(5);
        /**
         * 消费者消费消息 --> 采用手动应答方式（autoAck = false）
         * 参数1：队列名称
         * 参数2：消息成功消费之后是否需要自动应答，true代表自动应答，false代表手动应答（自动手动应答对应消息确认机制）
         * 参数3：接收消息时的回调
         * 参数4：消费者取消消费时的回调
         */
        channel.basicConsume("queue1", false, deliverCallback, cancelCallback);
    }
}




















