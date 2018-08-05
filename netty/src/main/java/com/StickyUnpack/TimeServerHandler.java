package com.StickyUnpack;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

public class TimeServerHandler extends ChannelInboundHandlerAdapter {
    int count;
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
       /* ByteBuf byteBuf = (ByteBuf) msg;//将请求转为ByteBuf缓冲区
        byte[] req = new byte[byteBuf.readableBytes()];//获取byteBuf的可读字节数
        byteBuf.readBytes(req);//将缓冲区字节数组复制到req数组中
        String body = new String(req,"utf-8")//转换为字符串
                //改造去掉客户端传递过来的换行符号，模拟故障造成粘包问题
             .substring(0,req.length-System.lineSeparator().length());*/
       String body = (String) msg;
        System.out.println("the time server receive order:"+body+"the count is:"+ ++count);
//      处理IO内容
        String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body)
                ?new Date(System.currentTimeMillis()).toString():"BAD ORDER";
        currentTime = currentTime+System.getProperty("line.separator");
        ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());//返回客户端的消息转化为ByteBuf对象
        ctx.write(resp);//将待应答消息放入缓冲数组中
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();//将应答消息写入SocketChannel中
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
