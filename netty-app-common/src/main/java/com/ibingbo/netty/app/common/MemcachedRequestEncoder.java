package com.ibingbo.netty.app.common;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.CharsetUtil;

/**
 * Created by bing on 17/6/13.
 */
public class MemcachedRequestEncoder extends MessageToByteEncoder<MemcachedRequest>{
    protected void encode(ChannelHandlerContext ctx, MemcachedRequest msg, ByteBuf out) throws Exception {
        byte[] key = msg.key().getBytes(CharsetUtil.UTF_8);
        byte[] body = msg.body().getBytes(CharsetUtil.UTF_8);
        int bodySize = key.length + body.length + (msg.hasExtras() ? 8 : 0);
        out.writeByte(msg.magic());
        out.writeByte(msg.opCode());
        out.writeShort(key.length);
        int extraSize = msg.hasExtras() ? 0x08 : 0x0;
        out.writeByte(extraSize);
        out.writeByte(0);
        out.writeShort(0);

        out.writeInt(bodySize);
        out.writeInt(msg.id());

        out.writeLong(msg.cas());

        if (msg.hasExtras()) {
            out.writeInt(msg.flags());
            out.writeInt(msg.expires());
        }

        out.writeBytes(key);
        out.writeBytes(body);

    }
}
