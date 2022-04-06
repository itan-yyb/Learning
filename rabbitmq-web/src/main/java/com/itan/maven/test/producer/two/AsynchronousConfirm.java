package com.itan.maven.test.producer.two;

import com.itan.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @Author: ye.yanbin
 * @Date: 2022/3/27
 * 消息生产者--异步confirm
 */
public class AsynchronousConfirm {
    public static void main(String[] args) throws Exception {
        //获取连接
        Connection connection = RabbitMQUtils.getConnection();
        //从连接中创建通道
        Channel channel = connection.createChannel();
        //开启发布确认
        channel.confirmSelect();
        /**
         * 并发线程安全有序的哈希表，用于存储没有确认的消息
         * 作用：
         *      1、将序号和消息进行关联（key：消息标识，value：消息内容）
         *      2、能够准确批量删除已经确认的消息
         *      3、支持并发访问
         */
        ConcurrentSkipListMap<Long, String> outstandingConfirmMap = new ConcurrentSkipListMap<>();
        //记录开始时间
        long start = System.currentTimeMillis();
        /**
         * 使用监听器异步confirm：监听器相当于启动了一个线程专门用于等待消息确认（Ack）
         * 可以使用带有一个参数的，也可以使用带有两个参数的
         * 一个参数的需要实例化ConfirmListener接口并重写里面的方法
         * 两个参数的可以使用lambda表达式，监听哪些成功消息，哪些失败消息
         */
        channel.addConfirmListener(new ConfirmListener() {
            /**
             * 消息确认成功回调
             * @param deliveryTag 表示返回的消息标识
             * @param multiple 表示是否为批量confirm
             * @throws IOException
             */
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                //判断是否为批量发送消息
                if (multiple) {
                    //收集已确认批量的消息
                    ConcurrentNavigableMap<Long, String> confirmedMap = outstandingConfirmMap.headMap(deliveryTag);
                    //删除已确认的所有消息，剩下的消息就是未确认的消息
                    confirmedMap.clear();
                } else {
                    //只删除当前已确认的消息
                    outstandingConfirmMap.remove(deliveryTag);
                }
                System.out.println("消息发送到交换机成功 -- 确认的消息标识：" + deliveryTag);
            }

            /**
             * 消息确认失败回调
             * @param deliveryTag 表示返回的消息标识
             * @param multiple 表示是否为批量confirm
             */
            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("消息发送到交换机失败 -- 未确认的消息标识：" + deliveryTag + " 消息内容：" + outstandingConfirmMap.get(deliveryTag));
            }
        });

        //发送1000条消息
        for (int i = 0; i < 1000; i++) {
            //消息内容
            String msg = i + "_" + UUID.randomUUID().toString().substring(0, 10);
            /**
             * 记录所有要发送的消息
             * channel.getNextPublishSeqNo()：获取下一个消息的序列号
             */
            outstandingConfirmMap.put(channel.getNextPublishSeqNo() ,msg);
            /**
             * 发送消息
             * 参数1：交换机名称，如果直接发送信息到队列，则交换机名称为""
             * 参数2：目标队列名称，表示要将消息发送到那个队列
             * 参数3：设置当前消息的属性（超时时间）
             * 参数4：消息的内容
             */
            channel.basicPublish("","queue1", null, msg.getBytes());
        }
        //记录结束时间
        long end = System.currentTimeMillis();
        //关闭通道和连接
        // channel.close();
        // connection.close();
        System.out.println("耗时：" + (end - start) / 1000.0 + " s");// 耗时：0.708 s
        Thread.sleep(10000);
        for (Long aLong : outstandingConfirmMap.keySet()) {
            System.out.println("消息发送到交换机失败 -- 未确认的消息标识：" + aLong + " 消息内容：" + outstandingConfirmMap.get(aLong));
        }
    }
}
