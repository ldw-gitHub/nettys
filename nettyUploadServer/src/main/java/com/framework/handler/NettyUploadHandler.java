package com.framework.handler;

import com.framework.config.RedisUtils;
import com.framework.constant.RedisKey;
import com.framework.model.ResultInfo;
import com.framework.util.ResponseUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.multipart.*;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description 文件上传
 * @author: liudawei
 * @date: 2020/1/19 15:56
 */
@Slf4j
public class NettyUploadHandler extends SimpleChannelInboundHandler<HttpObject> {

    RedisUtils redisUtils;

    public NettyUploadHandler(RedisUtils redisUtils){
        this.redisUtils = redisUtils;
    }

    private HttpRequest request;
    private static final String FILE_UPLOAD = "E:\\cesi";
    private static final String URL = "/post_multipart";
    private static final HttpDataFactory factory = new DefaultHttpDataFactory(DefaultHttpDataFactory.MINSIZE); // Disk if size exceed
    private HttpPostRequestDecoder decoder;

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        if (decoder != null) {
            decoder.cleanFiles();
            log.info("====================== decoder.cleanFiles() ===================");
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject httpObject) throws Exception {
        log.info("================================= NettyUploadHandler.channelRead0 =====================================");
        if (httpObject instanceof HttpRequest) {
            //第一次请求
            this.request = (HttpRequest) httpObject;
            log.info("request.uri() ==== " + request.uri());

            if (request.uri().equalsIgnoreCase(URL)) {
                decoder = new HttpPostRequestDecoder(factory, request);
                decoder.setDiscardThreshold(0);
            } else {
                log.info("========================= NettyUploadHandler.next  =======================");
                ResponseUtils.responseOK(ctx, new ResultInfo(ResultInfo.FAILURE, ResultInfo.MSG_FAILURE, "路径错误"));
            }
        }

        if (httpObject instanceof HttpContent) {
            if (decoder != null) {
                // 接收一个新的请求体
                decoder.offer((HttpContent) httpObject);
                if (httpObject instanceof LastHttpContent) {
                    // 将内存中的数据序列化本地
                    readHttpDataChunkByChunk();
                    log.info("======================= LastHttpContent =======================");
                    reset();
                    ResponseUtils.responseOK(ctx, new ResultInfo(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, "操作成功"));
                }
            } else {
                ResponseUtils.responseOK(ctx, new ResultInfo(ResultInfo.FAILURE, ResultInfo.MSG_FAILURE, "文件内容为空"));
            }
        }

    }

    private void readHttpDataChunkByChunk() throws IOException {
        log.info("readHttpDataChunkByChunk");
        while (decoder.hasNext()) {
            InterfaceHttpData data = decoder.next();
            log.info("data.getHttpDataType() === " + data.getHttpDataType());
            //获取其它参数信息
            log.info("data.size === " + decoder.getBodyHttpData("size"));
            if (data != null && InterfaceHttpData.HttpDataType.FileUpload.equals(data.getHttpDataType())) {
                final FileUpload fileUpload = (FileUpload) data;
                if (fileUpload.isCompleted()) {
                    File dir = new File(FILE_UPLOAD);
                    if (!dir.exists()) {
                        dir.mkdir();
                    }
                    File dest = new File(dir, fileUpload.getFilename());
                    fileUpload.renameTo(dest);
                    //将上传文件放入redis
                    redisUtils.set(RedisKey.DIR_PATH + fileUpload.getFilename(),fileUpload.getFilename());
                }
            }
        }

    }


    private void reset() {
        request = null;
        // destroy the decoder to release all resources
        decoder.destroy();
        decoder = null;
    }


    /**
     * @description:处理抛出的异常
     * @author: liudawei
     * @date: 2020/1/17 17:36
     * @param: ctx
     * @param: ex
     * @return: void
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable ex) {
        if (ex != null) {
            ex.printStackTrace();
            ResponseUtils.responseError(ctx, ex);
        }
    }
}
