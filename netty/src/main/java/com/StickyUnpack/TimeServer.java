package com.StickyUnpack;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class TimeServer {
    public void bind(int port) throws InterruptedException {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();//创建接收请求的线程组
        NioEventLoopGroup workGroup = new NioEventLoopGroup();//创建处理IO的线程组
        try {
            ServerBootstrap b = new ServerBootstrap();//创建NIO服务启动辅助类
            b.group(bossGroup,workGroup)//将两个线程组传递到NIO服务辅助启动类中
                    .channel(NioServerSocketChannel.class)//通过反射创建1一个severSocketChannel对象
                    .option(ChannelOption.SO_BACKLOG,1024)//BACKLOG用于构造服务端套接字ServerSocket对象，
                    // 标识当服务器请求处理线程全满时，用于临时存放已完成三次握手的请求的队列的最大长度。
                    // 如果未设置或所设置的值小于1，Java将使用默认值50。
                    .childHandler(new ChildHandler());//设置绑定的IO处理类，用来处理网络IO事件
//          绑定端口，同步等待成功，返回ChannelFutrue,用于异步操作通知回调
            ChannelFuture f = b.bind(port).sync();
//          等待服务端监听端口关闭
            f.channel().closeFuture().sync();
        }finally {
//            优雅的退出，释放线程池资源
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }

    }
//    IO处理类的初始化
    private class ChildHandler extends ChannelInitializer {
        @Override
        protected void initChannel(Channel channel) throws Exception {
            channel.pipeline()
                    .addLast(new LineBasedFrameDecoder(1024))
                    .addLast(new StringDecoder())
                    .addLast(new TimeServerHandler());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int port = 8080;
        try {
            if(args.length>0&&args[0]!=null){
                System.out.println(args[0]);
                port = Integer.valueOf(args[0]);
            }
        } catch (Exception e) {
            port = 8080;
        }
        new TimeServer().bind(port);
    }
}
