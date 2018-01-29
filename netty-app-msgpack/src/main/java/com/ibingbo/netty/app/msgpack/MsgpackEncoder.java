package com.ibingbo.netty.app.msgpack;

import org.msgpack.MessagePack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * MsgpackEncoder
 *
 * @author zhangbingbing
 * @date 18/1/26
 */
public class MsgpackEncoder extends MessageToByteEncoder<Object>{
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        MessagePack pack = new MessagePack();
        byte[] raw = pack.write(msg);
        out.writeBytes(raw);
    }
}
