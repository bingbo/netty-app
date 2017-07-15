package com.ibingbo.netty.app.client;

import com.ibingbo.netty.app.common.Request;
import com.ibingbo.netty.app.common.Response;
import com.ibingbo.netty.app.common.SerializationUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * Created by bing on 17/6/2.
 */
public class ClientHandler1 extends SimpleChannelInboundHandler<Response> {
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Response response) throws Exception {
        System.out.println("Client received: " + response.getResult().toString());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        for (int i = 0; i < 100; i++) {
//            ctx.write(Unpooled.copiedBuffer("netty rocks ! num " + i, CharsetUtil.UTF_8));
//        }
        ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks! end .....", CharsetUtil.UTF_8));
        Request request = new Request();
        request.setClassName("aaaa");
        request.setMethodName("bbbb");
        request.setRequestId("1111");
        ctx.writeAndFlush(request);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}

