package com.itan.nioweb.bio.file;

import com.itan.nioweb.bio.four.ThreadPool;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.UUID;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author: ye.yanbin
 * @Date: 2022/3/13
 * 服务端：接受客户端发送的文件并保存
 */
public class Server {
    public static void main(String[] args) throws IOException {
        System.out.println("服务端启动===");
        //定义一个ServerSocket对象进行服务端的端口注册
        ServerSocket ss = new ServerSocket(9999);
        //初始化线程池
        ThreadPoolExecutor pool = ThreadPool.getThreadPool();
        while (true) {
            //监听客户端的Socket连接请求，会等待，得到一个Socket管道
            Socket socket = ss.accept();
            //执行任务
            pool.execute(new Thread(() -> {
                try {
                    //从socket管道中得到一个字节输入流
                    InputStream in = socket.getInputStream();
                    //获取一个数据输入流读取客户端发送的数据
                    DataInputStream dis = new DataInputStream(in);
                    //读取客户端发送过来的文件后缀
                    String suffix = dis.readUTF();
                    //定义一个字节输出管道将客户端的文件数据写出
                    OutputStream os = new FileOutputStream("C:\\Users\\安然\\Desktop\\" + UUID.randomUUID() + suffix);
                    //从数据输入流中读取文件数据，写出到字节输出流中去
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = dis.read(buffer)) > 0) {
                        os.write(buffer, 0, len);
                    }
                    os.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }));
        }
    }
}
