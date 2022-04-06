package com.itan.maven.test.consumer.three;

import com.itan.utils.RabbitMQUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @Author: ye.yanbin
 * @Date: 2022/3/28
 * 代码方式构建死信队列
 */
public class InitDeadQueue {
    private static final String NORMAL_EXCHANGE = "normal_exchange";
    private static final String DEAD_EXCHANGE = "dead_exchange";
    private static final String NORMAL_QUEUE = "normal_queue";
    private static final String DEAD_QUEUE = "dead_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        //获取连接
        Connection connection = RabbitMQUtils.getConnection();
        //从连接中创建通道
        Channel channel = connection.createChannel();
        /**
         * 创建交换器
         * 参数1：交换器名称
         * 参数2：交换器类型
         * 参数3：是否持久化
         */
        channel.exchangeDeclare(NORMAL_EXCHANGE, BuiltinExchangeType.DIRECT, true);
        channel.exchangeDeclare(DEAD_EXCHANGE, BuiltinExchangeType.DIRECT, true);
        //队列的一些属性
        Map<String, Object> arguments = new HashMap<>();
        // arguments.put("x-message-ttl", 10000);//消息过期时间，单位ms，可以不设置，可以由生产者设置消息过期时间
        arguments.put("x-dead-letter-exchange", "dead_exchange");//设置消息过期后转发到哪个交换器
        arguments.put("x-dead-letter-routing-key", "b");//设置消息过期后由交换器路由到哪个队列的路由键
        arguments.put("x-max-length", 6);//设置队列的长度，能存储消息的个数
        /**
         * 创建队列，normal队列需要添加额外参数，这样才能让消息转发到死信队列中，死信队列直接创建就行
         * 参数1：队列名称
         * 参数2：消息是否持久化，队列的声明默认是存放到内存中的，如果rabbitmq重启会丢失，如果想重启之后还存在就要使队列持久化，
         *        保存到Erlang自带的Mnesia数据库中，当rabbitmq重启之后会读取该数据库
         * 参数3：该队列是否只供一个消费者进行消费 是否进行共享，true可以多个消费者消费
         * 参数4：是否自动删除，最后一个消费者端开连接以后该队列是否自动删除，true自动删除
         * 参数5：设置当前队列的参数
         */
        channel.queueDeclare(NORMAL_QUEUE, false, false, false, arguments);
        channel.queueDeclare(DEAD_QUEUE, false, false, false, null);
        /**
         * 设置交换器与队列的绑定关系
         * 参数1：队列名
         * 参数2：交换器名
         * 参数3：路由键
         */
        channel.queueBind(NORMAL_QUEUE, NORMAL_EXCHANGE, "a");
        channel.queueBind(DEAD_QUEUE, DEAD_EXCHANGE, "b");
        //关闭通道与连接
        channel.close();
        connection.close();
    }
}
