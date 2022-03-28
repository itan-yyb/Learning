package com.itan.producer.two;

import com.itan.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.util.UUID;

/**
 * @Author: ye.yanbin
 * @Date: 2022/3/27
 * 消息生产者--批量confirm
 */
public class BatchConfirm {
    public static void main(String[] args) throws Exception {
        //获取连接
        Connection connection = RabbitMQUtils.getConnection();
        //从连接中创建通道
        Channel channel = connection.createChannel();
        //开启发布确认
        channel.confirmSelect();
        //记录开始时间
        long start = System.currentTimeMillis();
        //批量确认消息大小
        int batchSize = 100;
        //未确认消息个数
        int outStandingCount = 0;
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
            //未确认次数加1
            outStandingCount++;
            //每发送batchSize条数的消息就确认一次
            if (outStandingCount == batchSize) {
                channel.waitForConfirms();
                //计数归0
                outStandingCount = 0;
            }
        }
        //为确保还有剩余没有确认的消息，再次确认
        if (outStandingCount > 0) {
            channel.waitForConfirms();
        }
        //记录结束时间
        long end = System.currentTimeMillis();
        //关闭通道和连接
        channel.close();
        connection.close();
        System.out.println("耗时：" + (end - start) / 1000.0 + " s");//耗时：0.975 s
    }
}
