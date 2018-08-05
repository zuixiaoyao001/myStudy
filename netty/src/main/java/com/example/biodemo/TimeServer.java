package com.example.biodemo;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TimeServer {
    public static void main(String[] args) throws IOException {
        int port = 8092;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                port = 8092;
            }
        }
        AsyncTimeServerHandler timeServer = new AsyncTimeServerHandler(port);
        new Thread(timeServer,"AsychronousTimeServerHandler").start();



        // ===================改造代码为AIO将以下内容注释掉=================================

//        创建多路复用线程类并初始化多路复用器，绑定端口等以及轮询注册功能
 /*       MultiplexerTimeServer timeServer = new MultiplexerTimeServer(port);
//        启动多路复用类线程负责轮询多路复用器Selector，IO数据处理等操作
        new Thread(timeServer, "NIO-MultiplexerTimeSever-001").start();*/

// ===================以下内容注释掉=================================

       /* ServerSocket server = null;
        try {
            server = new ServerSocket(port);
            System.out.println("the timeServer is start in port :" + port);
            Socket socket = null;
//           引入线程池start
            TimeServerHandlerExecutePool singleExecutor = new TimeServerHandlerExecutePool(50,10000);
            while (true) {
                socket = server.accept();
//           替换BIO中new Thread(new TimeServerHandler(socket)).start();为下一行代码
                singleExecutor.execute(new TimeServerHandler(socket));
//           引入线程池end

            }
        } finally {
            if (server != null) {
                System.out.println("the time server close");
                server.close();
                server = null;
            }
        }*/

    }
}




