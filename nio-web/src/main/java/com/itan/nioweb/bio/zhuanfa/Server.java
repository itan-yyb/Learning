package com.itan.nioweb.bio.zhuanfa;

import com.itan.nioweb.bio.four.ThreadPool;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author: ye.yanbin
 * @Date: 2022/3/13
 * 服务端：实现端口转发，接受客户端的消息，推送给当前所有在线的客户端接受
 */
public class Server {
    //用于存放所有连接的socket
    private static List<Socket> allSocket = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        //定义一个ServerSocket对象进行服务端的端口注册
        ServerSocket ss = new ServerSocket(9999);
        //初始化线程池
        ThreadPoolExecutor pool = ThreadPool.getThreadPool();
        while (true) {
            //监听客户端的Socket连接请求，会等待，得到一个Socket管道
            Socket socket = ss.accept();
            allSocket.add(socket);
            //执行任务
            pool.execute(new Thread(() -> {
                try {
                    //从socket管道中得到一个字节输入流
                    InputStream in = socket.getInputStream();
                    //把字节输入流包装成自己需要的流进行数据的读取
                    BufferedReader br = new BufferedReader(new InputStreamReader(in));
                    //读取数据
                    String msg;
                    while ((msg = br.readLine()) != null) {
                        //将消息推送到当前所有在线的socket
                        sendSocketOnLine(msg);
                    }
                } catch (Exception e) {
                    System.out.println(socket + "下线了");
                    //更新
                    allSocket.remove(socket);
                }
            }));
        }
    }

    /**
     * 推送消息
     * @param msg
     */
    private static void sendSocketOnLine(String msg) {
        allSocket.stream().forEach(s -> {
            try {
                PrintStream ps = new PrintStream(s.getOutputStream());
                ps.println(msg);
                ps.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
