package com.itan.nioweb.nio.channel;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

/**
 * @Author: ye.yanbin
 * @Date: 2022/3/13
 * 通过FileChannel读取数据到Buffer中
 */
public class FileChannelTest1 {
    public static void main(String[] args) throws IOException {
        //创建一个输入流对象用于创建FileChannel
        FileInputStream fis = new FileInputStream("C:\\Users\\安然\\Desktop\\测试文件.txt");
        //获取文件字节输入流的文件通道
        FileChannel channel = fis.getChannel();
        //获取关联文件的大小
        long size = channel.size();
        System.out.println("关联文件大小为：" + size);
        //创建Buffer缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //读取数据到Buffer中
        channel.read(buffer);
        //反转读写模式
        buffer.flip();
        //读取出缓冲区中的数据并输出
        String rs = new String(buffer.array(),0, buffer.remaining());
        System.out.println(rs);
        //关闭通道
        channel.close();
        //关流
        fis.close();
    }
}
/**
 * 关联文件大小为：6
 * Oracle
 */