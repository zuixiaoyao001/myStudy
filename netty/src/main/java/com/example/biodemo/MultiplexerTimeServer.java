package com.example.biodemo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class MultiplexerTimeServer implements Runnable {
    //    定义多路复用器
    private Selector selector;
    //    定义ServerSocketChannel
    private ServerSocketChannel servChannel;
    //    定义停止标识
    private boolean stop;

    //    构造函数初始化多路复用器、并绑定监听端口
    public MultiplexerTimeServer(int port) {
        try {
//            打开一个多路复用器
            selector = Selector.open();
//            打开ServerSocketChannel通道
            servChannel = ServerSocketChannel.open();
//            绑定监听端口号，并设置backlog为1024
            servChannel.socket().bind(new InetSocketAddress(port), 1024);
//            设置severSocketChannel为异步非阻塞模式
            servChannel.configureBlocking(false);
//            将ServerSocketChannel 注册到Reactor 线程的多路复用器Selector上，监听ACCEPT事件,并返回一个SelectionKey类型的值
            SelectionKey selectionKey = servChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("The time server is start in port:" + port);
        } catch (IOException ioe) {
            ioe.printStackTrace();
//            资源初始化失败则退出
            System.exit(1);
        }
    }

    public void stop() {
        this.stop = true;
    }

    @Override
    public void run() {
//        在线程中遍历轮询多路复用器selector
        while (!stop) {
            try {
             /*selector.select();选择一些I/O操作已经准备好的channel。每个channel对应着一个key。这个方法是一个阻塞的选择操作。
              当至少有一个通道被选择时才返回。当这个方法被执行时，当前线程是允许被中断的。*/
//            该方法是阻塞的，选择一组键，其相应的通道已为 I/O 操作准备就绪。最多等1s，如果还没有就绪的就返回0
            /*如果 timeout为正，则select(long timeout)在等待有通道被选择时至多会阻塞timeout毫秒
              如果timeout为零，则永远阻塞直到有至少一个通道准备就绪。
              timeout不能为负数*/
                selector.select(1000);
//            当有处于就绪状态的channel时，返回该channel的selectionKey集合，此通道是已准备就绪的键集，已选择键集（I/O操作已就绪返回key）始终是键集的一个子集。
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> it = selectionKeys.iterator();
//            Selector如果发现channel有OP_ACCEPT或READ事件发生，下列遍历就会进行。
                SelectionKey key = null;
                while (it.hasNext()) {
                    // 来一个事件 第一次触发一个accepter线程，SocketReadHandler
                    key = it.next();
//                    从iterator中移除该元素
                    it.remove();
                    try {
//                      去对该已经准备就绪的I/O操作进行处理
                        dispatch(key);
                    } catch (Exception e) {
//                        删除处理完不为空的键，关闭相关通道
                        if (key != null) {
                            key.cancel();
                            if (key.channel() != null) {
                                key.channel().close();
                            }
                        }
                    }
                }
            } catch (IOException ioe1) {
                ioe1.printStackTrace();
            }
        }
//        最后关闭多路复用器
        if (selector != null) {
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
//该方法用于处理轮询到的I/O已经就绪的SelectionKey键
    private void dispatch(SelectionKey key) throws IOException {
        if (key.isValid()) {
//            处理请求接入的信息
            if (key.isAcceptable()) {
//                接受新连接，通过SelectionKey获取其通道
                ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
//                接收客户端连接请求并创建SocketChannel实例
                SocketChannel sc = ssc.accept();
                sc.configureBlocking(false);
//                添加新连接到多路复用器上
                sc.register(selector, SelectionKey.OP_READ);
            }
            if (key.isReadable()) {
//                本方法体读取客户端发来的请求数据
                SocketChannel sc = (SocketChannel) key.channel();
//                开辟一个缓冲区，这里开辟了1M
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
//                读取请求码流，返回值>0,读到字节数，返回值=0，没有读到字节数，返回值<0,说明链路已经关闭，
//                需要关闭SocketChannel
                int readBytes = sc.read(readBuffer);
                if (readBytes > 0) {
//                    读到字节数后的解码操作，对 readBuffer 进行 flip 操作， 作用是将缓冲区当前的 limit 设置为 position
//                    position 设置为 0，用于后续对缓冲区的读取操作。
                    readBuffer.flip();
//                    根据缓冲区的可读的字节个数创建字节数组
                    byte[] bytes = new byte[readBuffer.remaining()];
//                   调用 ByteBuffer的 get 操作将缓冲区可读的字节数复制到新创建的字节数组中
                    readBuffer.get(bytes);
//                    调用字符串中的构造函数创建请求消息体并打印。
                    String body = new String(bytes, "utf-8");
                    System.out.println("The time server receive order :" + body);
                    String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new Date(System.currentTimeMillis()).toString() : "BAD ORDER";
//                    将应答消息异步发送给客户端
                    doWrite(sc, currentTime);
                } else if (readBytes < 0) {
//                    对端链路关闭
                    key.cancel();
                    sc.close();
                } else {
                    ;//读到0字节，忽略。
                }
            }
        }
    }

    private void doWrite(SocketChannel channel, String response) throws IOException {
        if (response != null && response.trim().length() > 0) {
//            将字符创编码为字节数组
            byte[] bytes = response.getBytes();
//            根据字节数组大小创建缓冲区
            ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
//            复制字节数组内容进入缓冲区
            writeBuffer.put(bytes);
//            进行flip操作，作用同上面
            writeBuffer.flip();
//            将缓冲区中的字节数组发送出去
            channel.write(writeBuffer);
        }
    }
}
