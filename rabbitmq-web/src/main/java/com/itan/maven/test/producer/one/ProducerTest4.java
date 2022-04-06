package com.itan.maven.test.producer.one;

import com.itan.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * @Author: ye.yanbin
 * @Date: 2022/3/24
 * 消息生产者 --- 路由模式
 */
public class ProducerTest4 {
    public static void main(String[] args) throws IOException, TimeoutException {
        System.out.println("请输入消息：");
        Scanner scanner = new Scanner(System.in);
        //消息内容
        String msg = null;
        while (!"exit".equals(msg = scanner.nextLine())) {
            //1、获取连接
            Connection connection = RabbitMQUtils.getConnection();
            //2、从连接中创建通道
            Channel channel = connection.createChannel();
            //若消息中包含a，则发送到路由键为a的队列中
            if (msg.contains("a")) {
                /**
                 * 3、发送消息
                 * 参数1：交换机名称，如果直接发送信息到队列，则交换机名称为""
                 * 参数2：不为空表示路由键（发送到绑定路由键对应的队列中），为空表示在此交换机中的所有队列都能接收到消息
                 * 参数3：设置当前消息的属性（超时时间）
                 * 参数4：消息的内容
                 */
                channel.basicPublish("direct_exchange","a", null, msg.getBytes());
            } else if (msg.contains("b")) {
                channel.basicPublish("direct_exchange","b", null, msg.getBytes());
            }
            System.out.println("发送消息：" + msg);
            //4、关闭通道和连接
            channel.close();
            connection.close();
        }
    }
}
