package com.example.restasync.client;

import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import org.springframework.boot.web.embedded.netty.NettyServerCustomizer;
import reactor.netty.http.server.HttpServer;

/*public class NettyCustomizer implements NettyServerCustomizer {

    private final EventLoopGroup bossGroup = new NioEventLoopGroup(32);
    private final EventLoopGroup workerGroup = new NioEventLoopGroup();

    @Override
    public HttpServer apply(HttpServer httpServer) {
        return httpServer.tcpConfiguration(tcpServer ->
                tcpServer.bootstrap(serverBootstrap ->
                        serverBootstrap
                                .group(bossGroup, workerGroup)
                                .channel(NioServerSocketChannel.class)
                                .handler(new LoggingHandler(LogLevel.DEBUG))
                                .childHandler(new ChannelInitializer<SocketChannel>() {
                                    @Override
                                    public void initChannel(final SocketChannel socketChannel) {
                                        socketChannel.pipeline().addLast(new BufferingInboundHandler());
                                    }
                                })
                                .option(ChannelOption.SO_BACKLOG, 128)
                                .childOption(ChannelOption.SO_KEEPALIVE, true))
                        .port(8899)
        );
    }
}*/
