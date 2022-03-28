package com.itan.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author: ye.yanbin
 * @Date: 2022/3/24
 * MQ连接工具类
 */
public class RabbitMQUtils {
    public static Connection getConnection() throws IOException, TimeoutException {
        //1、创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //2、设置RabbitMQ连接地址
        factory.setHost("101.35.155.147");
        //3、设置端口号
        factory.setPort(5672);
        //4、虚拟主机
        factory.setVirtualHost("test_host");
        //5、用户名
        factory.setUsername("test");
        //6、密码
        factory.setPassword("123456");
        //7、通过工厂对象获取与MQ的连接
        Connection connection = factory.newConnection();
        return connection;
    }

    public static Channel getChannel() throws IOException, TimeoutException {
        //获取连接
        Connection connection = getConnection();
        return connection.createChannel();
    }
}
