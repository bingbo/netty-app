package com.ibingbo.netty.app.msgpack;

import java.util.List;

import org.msgpack.MessagePack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

/**
 * MsgpackDecoder
 *
 * @author zhangbingbing
 * @date 18/1/26
 */
public class MsgpackDecoder extends MessageToMessageDecoder<ByteBuf>{
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        byte[] data = new byte[msg.readableBytes()];
        msg.readBytes(data);
        MessagePack pack = new MessagePack();
        out.add(pack.read(data));
    }
}
