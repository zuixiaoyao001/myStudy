package com.messagePack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.msgpack.MessagePack;

import java.util.List;

public class MsgPackDecoder extends MessageToMessageDecoder {
/*    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
//      获取要解码的byte数组
        final byte[] bytes;
        final int length = byteBuf.readableBytes();
        bytes = new byte[length];
        byteBuf.getBytes(byteBuf.readerIndex(),bytes,0,length);
//      调用MessagePack 的read方法将其反序列化为Object对象
        MessagePack msgPack = new MessagePack();
        list.add(msgPack.read(bytes));
    }*/

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, Object o, List list) throws Exception {
        ByteBuf byteBuf = (ByteBuf) o;
//      获取要解码的byte数组
        final byte[] bytes;
        final int length = byteBuf.readableBytes();
        bytes = new byte[length];
        byteBuf.getBytes(byteBuf.readerIndex(),bytes,0,length);
//      调用MessagePack 的read方法将其反序列化为Object对象
        MessagePack msgPack = new MessagePack();
        list.add(msgPack.read(bytes));
    }
}
