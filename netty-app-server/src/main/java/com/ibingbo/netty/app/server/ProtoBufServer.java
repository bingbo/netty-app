package com.ibingbo.netty.app.server;

import java.net.InetSocketAddress;

import com.ibingbo.netty.app.common.Decoder;
import com.ibingbo.netty.app.common.Encoder;
import com.ibingbo.netty.app.common.Request;
import com.ibingbo.netty.app.common.Response;
import com.ibingbo.netty.app.protobuf.AddressBookProtos;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;

/**
 * Created by bing on 17/6/2.
 */
public class ProtoBufServer {
    private final int port;

    public ProtoBufServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws Exception {
        new ProtoBufServer(8888).start();
    }

    public void start() throws Exception {
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(group)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast(new ProtobufVarint32FrameDecoder())
                                    .addLast(new ProtobufDecoder(AddressBookProtos.AddressBook.getDefaultInstance()))
                                    .addLast(new ProtoBufServerHandler());
                        }
                    });
            ChannelFuture f = b.bind().sync();
            System.out.println(ProtoBufServer.class.getName() + " started and listen on " + f.channel().localAddress());
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }
}
