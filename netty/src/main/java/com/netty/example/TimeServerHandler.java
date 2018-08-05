package com.netty.example;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

public class TimeServerHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;//将接收到的msg转换为netty的BytyBuf对象，注意区分jdk中的ByteBuffer
        byte[] req = new byte[buf.readableBytes()];//创建接收缓冲区数据的字节数组
        buf.readBytes(req);//将缓冲区的数据读取到字节数组中
        String body = new String(req,"utf-8");//获取请求消息
        System.out.println("The time server receive order:"+body);
        String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body)?new Date(System.currentTimeMillis()).toString():"BAD ORDER";
        ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());//将响应信息封装为netty 的ByteBuf对象，
        ctx.write(resp);//异步发送应答消息给客户端
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        /*将消息发送队列中的消息写道SocketChannel中发送给对方。从性能上考虑，为了防止频繁唤醒Selector进行消息发送
        * netty的write方法并不直接将消息写入socketChannel中，调用write方法只是把待发送的消息发送到数据缓冲数组中，
        * 再通过调用flush()方法，将发送缓冲区中的消息全部写道SocketChannel中*/
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();//发生异常时，关闭ChannelHandlerContext,释放ChannelHandlerContext相关联的句柄资源
    }
}
