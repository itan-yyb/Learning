package com.itan.nioweb.bio.file;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * @Author: ye.yanbin
 * @Date: 2022/3/13
 * 客户端：上传任意类型的文件数据给服务端保存起来
 */
public class Client {
    public static void main(String[] args) {
        try (InputStream is = new FileInputStream("C:\\Users\\安然\\Desktop\\20122211701000416.jpg")) {
            //创建一个Socket的通信管道，请求与服务端的端口连接
            Socket socket = new Socket("127.0.0.1", 9999);
            //从Socket通信管道中得到一个字节输出流
            OutputStream os = socket.getOutputStream();
            //把字节流包装成一个数据输出流
            DataOutputStream dos = new DataOutputStream(os);
            //先发送上传文件的后缀给服务端
            dos.writeUTF(".jpg");
            //读取文件数据并发送给服务端
            byte [] bytes = new byte[1024];
            int len;
            while ((len = is.read(bytes)) > 0) {
                dos.write(bytes, 0, len);
            }
            dos.flush();
            //通知服务端数据已经发送完毕，服务端不用阻塞等待
            socket.shutdownOutput();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
