package com.jinting.netty_example;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * channel注册后会执行里面相应的初始化方法
 *
 */
public class HelloSocketInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        //通过socketChannel去获取相应的管道
        ChannelPipeline pipeline = socketChannel.pipeline();

        //通过管道添加handler
        //HttpServerCodec是由netty自己提供的助手类，可以理解为拦截器
        //当请求到服务端，我们需要做解码，响应到客户端做编码
        pipeline.addLast("httpServerCodec",new HttpServerCodec());

        //添加自定义的助手类，返回"hello netty"
        pipeline.addLast("customHandler",new CustomerHandler());

    }
}
