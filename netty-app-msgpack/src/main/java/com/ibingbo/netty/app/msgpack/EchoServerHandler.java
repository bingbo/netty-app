package com.ibingbo.netty.app.msgpack;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * EchoServerHandler
 *
 * @author zhangbingbing
 * @date 18/1/26
 */
public class EchoServerHandler extends ChannelInboundHandlerAdapter{

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("receive client : [" + msg + "]");
        ctx.writeAndFlush("got message : " + msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
