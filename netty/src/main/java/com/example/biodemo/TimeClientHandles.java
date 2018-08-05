package com.example.biodemo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

//该类用来处理异步连接和读写操作
public class TimeClientHandles implements Runnable {
    private String host;
    private int port;
    private Selector selector;
    private SocketChannel socketChannel;
    private volatile boolean stop;

    //    构造函数初始化并连接服务器
    public TimeClientHandles(String host, int port) {
        this.host = host == null ? "127.0.0.1" : host;
        this.port = port;
        try {
            selector = Selector.open();
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public void run() {
//        发送连接请求
        try {
            doConnection();
        }catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }
//      在循环体内轮询多路复用器Selector,当有就绪的Channel时，执行handleInput(key);
        while (!stop) {
            try {
                selector.select(1000);
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> it = keys.iterator();
                SelectionKey key = null;
                while (it.hasNext()) {
                    key = it.next();
                    it.remove();
                    try {
                        handleInput(key);
                    } catch (Exception e) {
                        if (key != null) {
                            key.cancel();
                            if (key.channel() != null) {
                                key.channel().close();
                            }
                        }
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void handleInput(SelectionKey key) throws IOException {
        if (key.isValid()) {
//            判断是否连接成功（需要判断连接状态和连接结果是否成功）
            SocketChannel sc = (SocketChannel) key.channel();
//            连接状态判断，是连接状态返回true，判断的是服务端是否已经返回ACK应答消息
            if (key.isConnectable()) {
//                是连接状态则需要对连接结果进行判断，如果true，说明客户端连接成功，如果flase则抛出IO异常,连接失败
                if(sc.finishConnect()){
                    sc.register(selector, SelectionKey.OP_READ);
                    doWrite(sc);
                }else {
//                    连接失败则进程退出
                    System.exit(1);
                }
            }
            if (key.isReadable()) {
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                int readBytes = sc.read(readBuffer);
                if (readBytes > 0) {
                    readBuffer.flip();
                    byte[] bytes = new byte[readBuffer.remaining()];
                    readBuffer.get(bytes);
                    String body = new String(bytes, "utf-8");
                    System.out.println("Now is :" + body);
                    this.stop = true;
                } else if (readBytes < 0) {
                    key.cancel();
                    sc.close();
                } else {
                    ;//没有读到字节什么都不做
                }
            }
        }
    }

    private void doConnection() {
        try {
//            如果直接连接成功，则注册到多路复用器上，并注册SelectionKey.OP_READ,发送请求消息，读应答
            if (socketChannel.connect(new InetSocketAddress(host, port))) {
                socketChannel.register(selector, SelectionKey.OP_READ);
                doWrite(socketChannel);
            } else {
//                连接不成功说明服务端没有返回TCP握手应答消息，但是不代表连接失败，注册socketChannel到多路复用器上，
//                并注册SelectionKey.OP_CONNECT,当服务器返回TCP syn-ack 消息后，Selector 就能轮询到这个SocketChannel
//                处于连接就绪状态
                socketChannel.register(selector, SelectionKey.OP_CONNECT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void doWrite(SocketChannel socketChannel) throws IOException {
        byte[] req = "QUERY TIME ORDER".getBytes();
        ByteBuffer writeBuffer = ByteBuffer.allocate(req.length);
        writeBuffer.put(req);
        writeBuffer.flip();
        socketChannel.write(writeBuffer);
        if (!writeBuffer.hasRemaining()) {
            System.out.println("send order 2 server succeed.");
        }

    }
}
