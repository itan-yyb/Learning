package com.itan.nioweb.nio.channel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author: ye.yanbin
 * @Date: 2022/3/14
 * 向FileChannel中写数据
 */
public class FileChannelTest2 {
    public static void main(String[] args) throws IOException {
        //创建一个输出流对象用于创建FileChannel
        FileOutputStream fis = new FileOutputStream("C:\\Users\\安然\\Desktop\\测试文件.txt");
        //获取文件字节输入流的文件通道
        FileChannel channel = fis.getChannel();
        //创建Buffer缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //向缓冲区添加要写入的内容
        String msg = "Oracle \nJAVA \n MYSQL";
        buffer.put(msg.getBytes());
        //反转读写模式
        buffer.flip();
        //因为无法保证write方法一次能向FileChannel写入多少字节，因此需要重复调用write方法，直到buffer中已经没有尚未写入通道的字节
        while (buffer.hasRemaining()) {
            channel.write(buffer);
        }
        //关闭通道
        channel.close();
        //关流
        fis.close();
    }
}
