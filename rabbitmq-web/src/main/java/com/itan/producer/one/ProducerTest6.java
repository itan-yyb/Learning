package com.itan.producer.one;

import com.itan.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author: ye.yanbin
 * @Date: 2022/3/27
 * 消息生产者--事务模式
 */
public class ProducerTest6 {
    public static void main(String[] args) throws IOException, TimeoutException {
        //获取连接
        Connection connection = RabbitMQUtils.getConnection();;
        //从连接中创建通道
        Channel channel = connection.createChannel();
        //消息内容
        String msg = "事务测试";
        try {
            //开启事务
            channel.txSelect();
            /**
             * 发送消息
             * 参数1：交换机名称，如果直接发送信息到队列，则交换机名称为""
             * 参数2：目标队列名称，表示要将消息发送到那个队列
             * 参数3：设置当前消息的属性
             * 参数4：消息的内容
             */
            channel.basicPublish("test_exchange","a", null, msg.getBytes());
            // int result = 1 / 0;
            //提交事务
            channel.txCommit();
            System.out.println("发送消息：" + msg);
        } catch (Exception e) {
            //事务回滚
            channel.txRollback();
            e.printStackTrace();
        }
        //关闭通道和连接
        channel.close();
        connection.close();
    }
}
