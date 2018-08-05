package com.example.biodemo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;

public class AsyncTimeServerHandler implements Runnable{
     private int port;
//     添加它作用是在完成一组正在执行的操作之前，允许当前线程一直阻塞
     CountDownLatch latch;
     AsynchronousServerSocketChannel asynchronousServerSocketChannel;
    public AsyncTimeServerHandler(int port) {
        this.port=port;
        try {
//            创建异步服务套接字通道
            asynchronousServerSocketChannel = AsynchronousServerSocketChannel.open();
//            绑定监听端口
            asynchronousServerSocketChannel.bind(new InetSocketAddress(port));
            System.out.println("The time server is start in port"+port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
    latch =new CountDownLatch(1);
//    接收客户端的连接
    doAccept();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void doAccept() {
//        AcceptCompletionHandler用于接收accept操作成功的通知消息
//        accept(A attachment, CompletionHandler<AsynchronousSocketChannel,? super A> handler)：接受连接，并为连接绑定一个CompletionHandler处理Socket连接
        asynchronousServerSocketChannel.accept(this,new AcceptCompletionHandler());
    }
}
