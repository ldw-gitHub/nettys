package com.framework.init;

import com.framework.config.RedisUtils;
import com.framework.handler.NettyBreakpointUploadHandler;
import com.framework.handler.NettyUploadHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @description: netty初始化拦截器
 * @author: liudawei
 * @date: 2020/1/14 10:18
 */
public class NettyServerInitializer extends ChannelInitializer<SocketChannel> {

    RedisUtils redisUtils;

    public NettyServerInitializer(RedisUtils redisUtils){
        this.redisUtils = redisUtils;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();//管道，添加拦截器

        /** ------------------------- 不续传 -----------------------------*/
//        pipeline.addLast("HttpRequestDecoder",new HttpRequestDecoder());
//        pipeline.addLast("HttpResponseEncoder",new HttpResponseEncoder());
//        pipeline.addLast("HttpContentCompressor",new HttpContentCompressor());
//        pipeline.addLast("NettyUploadHandler", new NettyUploadHandler(redisUtils));

        /** ------------------------- 断点续传 ----------------------------*/
        pipeline.addLast(new ObjectEncoder());
        pipeline.addLast(new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.weakCachingConcurrentResolver(null))); // 最大长度
        pipeline.addLast(new NettyBreakpointUploadHandler());


    }
}
