package com.itan.maven.test.producer.one;

import com.itan.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author: ye.yanbin
 * @Date: 2022/3/24
 * 消息生产者--简单模式
 */
public class ProducerTest1 {
    public static void main(String[] args) throws IOException, TimeoutException {
        //消息内容
        String msg = "Hello World";
        //1、获取连接
        Connection connection = RabbitMQUtils.getConnection();
        //2、从连接中创建通道
        Channel channel = connection.createChannel();
        /**
         * 定义队列（使用java代码在MQ中新建一个队列）
         * 参数1：队列名称
         * 参数2：消息是否持久化，队列的声明默认是存放到内存中的，如果rabbitmq重启会丢失，如果想重启之后还存在就要使队列持久化，
         *        保存到Erlang自带的Mnesia数据库中，当rabbitmq重启之后会读取该数据库
         * 参数3：该队列是否只供一个消费者进行消费 是否进行共享，true可以多个消费者消费
         * 参数4：是否自动删除，最后一个消费者端开连接以后该队列是否自动删除， true自动删除
         * 参数5：设置当前队列的参数
         */
        channel.queueDeclare("queue1",true,false,false,null);
        /**
         * 3、发送消息
         * 参数1：交换机名称，如果直接发送信息到队列，则交换机名称为""
         * 参数2：目标队列名称，表示要将消息发送到那个队列
         * 参数3：设置当前消息的属性（超时时间）
         * 参数4：消息的内容
         */
        channel.basicPublish("","queue1", MessageProperties.PERSISTENT_TEXT_PLAIN, msg.getBytes());
        System.out.println("发送消息：" + msg);
        //4、关闭通道和连接
        channel.close();
        connection.close();
    }
}
