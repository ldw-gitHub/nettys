package com.framework.init;

import com.framework.handler.NettyInboundHandler;
import com.framework.handler.NettyVerificationHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.*;

/**
 * @description: netty初始化拦截器
 * @author: liudawei
 * @date: 2020/1/14 10:18
 */
public class NettyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();//管道，添加拦截器
        pipeline.addLast("httpServerCodec",new HttpServerCodec()); //完成http的编码与解码
        //将HTTP消息的多个部分合成一条完整的HTTP消息
        pipeline.addLast("http-aggregator",new HttpObjectAggregator(65535));
        //自定义拦截器
        pipeline.addLast("NettyVerificationHandler",new NettyVerificationHandler());
        pipeline.addLast("NettyInboundHandler",new NettyInboundHandler());
    }
}
