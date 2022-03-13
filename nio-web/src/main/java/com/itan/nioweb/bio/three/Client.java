package com.itan.nioweb.bio.three;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * @Author: ye.yanbin
 * @Date: 2022/3/13
 * 客户端：用于发送消息到服务端（多发）
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
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("客户端：");
            String send = sc.nextLine();
            ps.println(send);
            ps.flush();
        }
    }
}
