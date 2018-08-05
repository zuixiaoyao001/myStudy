package com.example.biodemo;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class AcceptCompletionHandler implements CompletionHandler<AsynchronousSocketChannel,AsyncTimeServerHandler>{

    @Override
    public void completed(AsynchronousSocketChannel result, AsyncTimeServerHandler attachment) {
        //再次让asynchronousServerSocketChannel对象调用accept方法是
        //调用AsynchronousServerSocketChannel的accept方法后，如果有新的客户端接入，
        // 系统将回调我们传入的CompletionHandler实例的completed方法，表示新客户端连接成功。
        // 因为AsynchronousServerSocketChannel可以接受成千上万个客户端，所以需要继续调用它的accept方法，
        // 接受其他客户端连接，最终形成一个环；每当一个客户端连接成功后，再异步接受新的客户端连接
        attachment.asynchronousServerSocketChannel.accept(attachment,this);
//        据上，链路建立成功，服务端需要接受客户端新请求消息，
        ByteBuffer buffer = ByteBuffer.allocate(1024);
//        调用read方法进行异步读操作
        result.read(buffer,buffer,new ReadCompletionHandler(result));
    }

    @Override
    public void failed(Throwable exc, AsyncTimeServerHandler attachment) {
        exc.printStackTrace();
        attachment.latch.countDown();
    }


}