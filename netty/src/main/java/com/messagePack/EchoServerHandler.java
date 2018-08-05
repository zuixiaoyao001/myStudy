package com.messagePack;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.List;

public class EchoServerHandler extends ChannelInboundHandlerAdapter {
    int count;
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("server receive the msgpack message : "+msg+"");
        // 原路返回给客户端
        ctx.writeAndFlush(msg);
/*        在EchoClientHandler中向服务端发送一个pojo对象，经过MessagePack编解码后，
        在EchoServerHandler中的channelRead方法中打印的msg为pojo对象的toString方法内容，
        不可以直接将msg转换为User,如果采用如下代码运行不成功*/
/*        List<User> users = (List<User>) msg;
        System.out.println("到这里面来了,users是否为空：");
        System.out.println(users!=null);
        for(User u : users){
            System.out.println("This is"+ ++count +" times server receive client request."+u);
            ctx.write(u);
        }

        ctx.flush();*/
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
