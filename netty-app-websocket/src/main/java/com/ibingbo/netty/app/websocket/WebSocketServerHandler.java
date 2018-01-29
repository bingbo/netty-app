package com.ibingbo.netty.app.websocket;

import java.util.Date;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.util.CharsetUtil;

/**
 * WebSocketServerHandler
 *
 * @author zhangbingbing
 * @date 18/1/26
 */
public class WebSocketServerHandler extends SimpleChannelInboundHandler<Object>{

    private WebSocketServerHandshaker handshaker;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
            handleHttpRequest(ctx, (FullHttpRequest) msg);
        } else if (msg instanceof WebSocketFrame) {
            handlerWebSocketFrame(ctx, (WebSocketFrame) msg);
        }
    }

    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest msg) {
        if (!msg.getDecoderResult().isSuccess() || (!"websocket".equals(msg.headers().get("Upgrade")))) {
            sendHttpResponse(ctx, msg,
                    new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            return;
        }

        WebSocketServerHandshakerFactory factory = new WebSocketServerHandshakerFactory
                ("ws://localhost:8080/websocket", null, false);
        handshaker = factory.newHandshaker(msg);
        if (handshaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedWebSocketVersionResponse(ctx.channel());
        } else {
            handshaker.handshake(ctx.channel(), msg);
        }
    }

    private void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest msg,
                                  DefaultFullHttpResponse defaultFullHttpResponse) {
        if (defaultFullHttpResponse.getStatus().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(defaultFullHttpResponse.getStatus().toString(), CharsetUtil.UTF_8);
            defaultFullHttpResponse.content().writeBytes(buf);
        }

        ChannelFuture future = ctx.channel().writeAndFlush(defaultFullHttpResponse);
        future.addListener(ChannelFutureListener.CLOSE);
    }

    private void handlerWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame msg) {
        if (msg instanceof CloseWebSocketFrame) {
            handshaker.close(ctx.channel(),(CloseWebSocketFrame)msg.retain());
            return;
        }
        if (msg instanceof PingWebSocketFrame) {
            ctx.channel().write(new PongWebSocketFrame(msg.content().retain()));
            return;
        }
        if (!(msg instanceof TextWebSocketFrame)) {
            throw new UnsupportedOperationException();
        }

        String request = ((TextWebSocketFrame) msg).text();
        ctx.channel().write(new TextWebSocketFrame(msg + new Date().toString()));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {

    }
}
