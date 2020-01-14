package com.framework.init;

import com.framework.handler.NettyHttpServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @description: netty初始化拦截器
 * @author: liudawei
 * @date: 2020/1/14 10:18
 */
public class NettyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("httpServerCodec",new HttpServerCodec()); //完成http的编码与解码
        //将HTTP消息的多个部分合成一条完整的HTTP消息
        pipeline.addLast("http-aggregator",new HttpObjectAggregator(65535));
        pipeline.addLast("HttpServerHandler",new NettyHttpServerHandler());
    }
}
