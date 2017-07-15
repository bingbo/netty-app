package com.ibingbo.netty.app.server;

import com.ibingbo.netty.app.common.Request;
import com.ibingbo.netty.app.common.Response;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * Created by bing on 17/6/2.
 */
public class ServerHandler1 extends SimpleChannelInboundHandler<Request>{
    @Override
    public void channelRead0(ChannelHandlerContext ctx, Request request) throws Exception {
        System.out.println("server received: " + request.getRequestId());
        Response response = new Response();
        response.setRequestId(request.getRequestId());
        response.setResult("method name : "+request.getMethodName());
        ctx.write(response);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
