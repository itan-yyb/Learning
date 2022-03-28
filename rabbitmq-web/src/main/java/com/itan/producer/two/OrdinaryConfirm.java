package com.itan.producer.two;

import com.itan.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

/**
 * @Author: ye.yanbin
 * @Date: 2022/3/27
 * 消息生产者--普通confirm
 */
public class OrdinaryConfirm {
    public static void main(String[] args) throws Exception {
        //获取连接
        Connection connection = RabbitMQUtils.getConnection();
        //从连接中创建通道
        Channel channel = connection.createChannel();
        //开启发布确认
        channel.confirmSelect();
        //记录开始时间
        long start = System.currentTimeMillis();
        //发送1000条消息
        for (int i = 0; i < 1000; i++) {
            //消息内容
            String msg = i + "_" + UUID.randomUUID().toString().substring(0, 10);
            /**
             * 发送消息
             * 参数1：交换机名称，如果直接发送信息到队列，则交换机名称为""
             * 参数2：目标队列名称，表示要将消息发送到那个队列
             * 参数3：设置当前消息的属性（超时时间）
             * 参数4：消息的内容
             */
            channel.basicPublish("","queue1", null, msg.getBytes());
            //获取确认，若服务端返回false或超时时间内未返回，生产者可以消息重发
            boolean flag = channel.waitForConfirms();
            if (flag) {
                System.out.println("消息发送成功：" + msg);
            }
        }
        //记录结束时间
        long end = System.currentTimeMillis();
        //关闭通道和连接
        channel.close();
        connection.close();
        System.out.println("耗时：" + (end - start) / 1000.0 + " s");//耗时：24.688 s
    }
}
