package com.jinting.netty_example;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;


/**
 * hello netty工程
 * 1.构建一对主从线程组
 * 2.定义服务器启动类
 * 3.为服务器设置channel
 * 4.设置从线程组的助手类初始化器
 *
 */
public class HelloNetty {

    public static void main(String[] args) throws InterruptedException {

        //1.定义一对线程组
        //主线程组,用于接收客户段链接，不做事
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //从线程组，负责处理任务
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try{
            //2.定义服务端启动类，serverbootstrap是一个启动类
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            serverBootstrap.group(bossGroup,workerGroup)//group设置线程模型
                    .channel(NioServerSocketChannel.class)//设置客户端和服务端的通道类型，nio的双向通道
                    .childHandler(new HelloSocketInitializer());//3.子处理器，设置从线程池助手类初始化器，相当于拦截器

            //启动server,启动方式为同步，端口8080
            ChannelFuture channelFuture = serverBootstrap.bind(8080).sync();
            //监听关闭的channel,设置位同步放置
            channelFuture.channel().closeFuture().sync();

        } finally {
            //优雅关闭
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }



    }
}

