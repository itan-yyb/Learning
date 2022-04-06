package com.itan.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: ye.yanbin
 * @Date: 2022/3/29
 * 创建交换器、队列及绑定关系
 */
@Configuration
public class RabbitMQInitConfig {
    private static final String NORMAL_EXCHANGE = "normal_exchange";
    private static final String DEAD_EXCHANGE = "dead_exchange";
    private static final String NORMAL_QUEUE = "normal_queue";
    private static final String DEAD_QUEUE = "dead_queue";

    /**
     * 声明交换器，几种不同类型的交换机对应的类如下：
     * 1、DirectExchange：声明路由模式交换器
     * 2、FanoutExchange：声明订阅模式交换器
     * 3、TopicExchange：声明带有模式匹配的交换器
     * @return
     */
    @Bean("normalExchange")
    public DirectExchange normalExchange() {
        return new DirectExchange(NORMAL_EXCHANGE);
    }

    @Bean("deadExchange")
    public DirectExchange deadExchange() {
        return new DirectExchange(DEAD_EXCHANGE);
    }

    /**
     * 声明队列有两种方式：
     * 1、使用QueueBuilder构建
     * 2、使用new Queue()
     * @return
     */
    @Bean("normalQueue")
    public Queue normalQueue() {
        //队列的一些属性
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-message-ttl", 10000);//消息过期时间，单位ms，可以不设置，可以由生产者设置消息过期时间
        arguments.put("x-dead-letter-exchange", DEAD_EXCHANGE);//设置消息过期后转发到哪个交换器
        arguments.put("x-dead-letter-routing-key", "b");//设置消息过期后由交换器路由到哪个队列的路由键
        // arguments.put("x-max-length", 6);//设置队列的长度，能存储消息的个数
        /**
         * 声明一个持久的队列
         * durable：持久的
         * withArguments：添加队列的属性（批量方式）
         * withArgument：添加队列的属性（单个方式）
         */
        return QueueBuilder.durable(NORMAL_QUEUE).withArguments(arguments).build();
    }

    @Bean("deadQueue")
    public Queue deadQueue() {
        //默认是一个持久队列
        Queue queue = new Queue(DEAD_QUEUE);
        return queue;
    }

    /**
     * 将队列绑定到交换器
     * @return
     */
    @Bean
    public Binding normalQueueBindingNormalExchange(@Qualifier("normalQueue") Queue normalQueue, @Qualifier("normalExchange") DirectExchange normalExchange) {
        //绑定队列到那个交换器，并指定路由键
        return BindingBuilder.bind(normalQueue).to(normalExchange).with("a");
    }

    @Bean
    public Binding deadQueueBindingDeadExchange(@Qualifier("deadQueue") Queue deadQueue, @Qualifier("deadExchange") DirectExchange deadExchange) {
        //绑定队列到那个交换器，并指定路由键
        return BindingBuilder.bind(deadQueue).to(deadExchange).with("b");
    }

    /**
     * 消息类型转换器
     */
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}