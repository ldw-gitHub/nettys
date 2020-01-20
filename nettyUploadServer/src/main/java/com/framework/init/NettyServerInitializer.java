package com.framework.init;

import com.framework.handler.NettyUploadHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

/**
 * @description: netty初始化拦截器
 * @author: liudawei
 * @date: 2020/1/14 10:18
 */
public class NettyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();//管道，添加拦截器
        pipeline.addLast("HttpRequestDecoder",new HttpRequestDecoder());
        pipeline.addLast("HttpResponseEncoder",new HttpResponseEncoder());
        pipeline.addLast("HttpContentCompressor",new HttpContentCompressor());
        //自定义拦截器
        pipeline.addLast("NettyUploadHandler",new NettyUploadHandler());
    }
}
