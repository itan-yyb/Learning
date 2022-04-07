package com.itan.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import javax.annotation.PostConstruct;

/**
 * @Author: ye.yanbin
 * @Date: 2022/4/7
 * 设置确认回调
 */
@Slf4j
@Configuration
public class RabbitTemplateConfig {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * @PostConstruct：RabbitTemplateConfig对象创建完成之后，执行这个注解标识的方法
     */
    @PostConstruct
    public void RabbitTemplateInit() {
        //设置确认回调，MQ只要收到消息，此方法会自动回调
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            /**
             * 用于监听消息确认结果（消息是否发送到交换机），只要成功抵达（即ack=ture）
             * @param correlationData：当前消息的唯一关联数据（里面包含消息id）
             * @param ack：消息是否成功抵达
             * @param cause：失败的原因
             */
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                log.info("消息成功到达MQ");
                log.info("当前消息的数据：{}", correlationData);
                log.info("消息是否成功抵达：{}", ack);
                log.info("失败原因：{}", cause);
            }
        });
        // 设置消息抵达队列的确认回调，消息正确抵达队列不会回调此方法，如果没有抵达，则会触发此回调
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            /**
             * 只要消息没有正确投递到队列，就触发这个失败的回调
             * @param message：投递失败的消息详细信息
             * @param replyCode：回复的状态码
             * @param replyText：回复的文本内容
             * @param exchange：这个消息发送到哪个交换器
             * @param routingKey：这个消息用哪个路由键
             */
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                log.info("消息抵达队列失败");
                log.info("当前消息的内容：{}", message);
                log.info("回复的状态码：{}", replyCode);
                log.info("回复的文本内容：{}", replyText);
                log.info("交换机：{}", exchange);
                log.info("路由键：{}", routingKey);
            }
        });
    }
}
