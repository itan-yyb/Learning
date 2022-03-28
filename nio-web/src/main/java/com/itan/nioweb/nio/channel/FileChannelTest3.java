package com.itan.nioweb.nio.channel;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author: ye.yanbin
 * @Date: 2022/3/13
 * 通过FileChannel读取数据到Buffer中
 */
public class FileChannelTest3 {
    public static void main(String[] args) throws IOException {
        //创建一个输入流对象用于创建FileChannel
        FileInputStream fis = new FileInputStream("C:\\Users\\安然\\Desktop\\测试文件.txt");
        //获取文件字节输入流的文件通道
        FileChannel channel = fis.getChannel();
        //获取FileChannel当前位置
        long position = channel.position();
        System.out.println("FileChannel当前位置：" + position);
        //创建Buffer缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //读取数据到Buffer中
        channel.read(buffer);
        //反转读写模式
        buffer.flip();
        //读取出缓冲区中的数据并输出
        String rs1 = new String(buffer.array(),0, buffer.remaining());
        System.out.println("源文件数据：" + rs1);
        //设置FileChannel当前位置
        channel.position(3);
        System.out.println("FileChannel当前位置：" + channel.position());
        //读取数据到Buffer中
        channel.read(buffer);
        //反转读写模式
        buffer.flip();
        //读取出缓冲区中的数据并输出
        String rs2 = new String(buffer.array(),0, buffer.remaining());
        System.out.println("设置位置之后的数据：" + rs2);
        //关闭通道
        channel.close();
        //关流
        fis.close();
    }
}
/**
 * FileChannel当前位置：0
 * 源文件数据：Oracle
 * FileChannel当前位置：3
 * 设置位置之后的数据：cle
 */