package com.itan.nioweb.bio.one;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

/**
 * @Author: ye.yanbin
 * @Date: 2022/3/13
 * 客户端：用于发送消息到服务端
 */
public class Client {
    public static void main(String[] args) throws IOException {
        System.out.println("客户端启动===");
        //创建一个Socket的通信管道，请求与服务端的端口连接
        Socket socket = new Socket("127.0.0.1", 9999);
        //从Socket通信管道中得到一个字节输出流
        OutputStream os = socket.getOutputStream();
        //把字节流包装成自己需要的流进行数据的发送
        PrintStream ps = new PrintStream(os);
        //向服务端发送消息
        ps.print("服务端你好，我是客户端");//没有按行发送消息
        // ps.println("服务端你好，我是客户端");//按行发送消息
        ps.flush();
    }
}
