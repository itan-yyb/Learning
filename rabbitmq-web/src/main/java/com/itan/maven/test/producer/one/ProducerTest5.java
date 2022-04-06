package com.itan.maven.test.producer.one;

import com.itan.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * @Author: ye.yanbin
 * @Date: 2022/3/26
 * 消息生产者--消息手动应答
 */
public class ProducerTest5 {
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
            /**
             * 3、发送消息
             * 参数1：交换机名称，如果直接发送信息到队列，则交换机名称为""
             * 参数2：目标队列名称，表示要将消息发送到那个队列
             * 参数3：设置当前消息的属性（超时时间）
             * 参数4：消息的内容
             */
            channel.basicPublish("","queue1", null, msg.getBytes());
            System.out.println("发送消息：" + msg);
            //4、关闭通道和连接
            channel.close();
            connection.close();
        }
    }
}
