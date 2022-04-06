package com.itan.maven.test.producer.two;

import com.itan.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @Author: ye.yanbin
 * @Date: 2022/3/24
 * 消息生产者--topic类型交换器
 */
public class TopicProducer {
    public static void main(String[] args) throws IOException, TimeoutException {
        //获取连接
        Connection connection = RabbitMQUtils.getConnection();
        //从连接中创建通道
        Channel channel = connection.createChannel();
        //消息集合k：路由键，v：消息内容
        Map<String, String> bindingKeyMap = new HashMap<>();
        bindingKeyMap.put("com.itan.client", "消息发送到topic_queue1与topic_queue2");
        bindingKeyMap.put("com.java.client", "消息发送到topic_queue2");
        bindingKeyMap.put("com.demo", "消息发送到topic_queue2");
        bindingKeyMap.put("java.itan.demo", "消息发送到topic_queue1");
        bindingKeyMap.put("java.util.demo", "没有接收队列");
        //循环发送消息
        for (Map.Entry<String, String> entry : bindingKeyMap.entrySet()) {
            /**
             * 发送消息
             * 参数1：交换机名称，如果直接发送信息到队列，则交换机名称为""
             * 参数2：目标队列名称，表示要将消息发送到那个队列
             * 参数3：设置当前消息的属性
             * 参数4：消息的内容
             */
            channel.basicPublish("topic_exchange",entry.getKey(), null, entry.getValue().getBytes());
            System.out.println("路由键为：" + entry.getKey() + " 消息内容：" + entry.getValue());
        }
        //关闭通道和连接
        channel.close();
        connection.close();
    }
}
