package com.framework.handler;

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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * @description
 * @author: liudawei
 * @date: 2020/1/19 15:56
 */
@Slf4j
public class NettyUploadHandler extends SimpleChannelInboundHandler<HttpObject> {


    private HttpRequest request;
    private static final String FILE_UPLOAD = "/data/";
    private static final String URL = "/post_multipart";
    private static final HttpDataFactory factory = new DefaultHttpDataFactory(DefaultHttpDataFactory.MINSIZE); // Disk if size exceed
    private HttpPostRequestDecoder decoder;

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        if (decoder != null) {
            decoder.cleanFiles();
        }
    }

    protected void channelRead0(ChannelHandlerContext ctx, HttpObject httpObject) throws Exception {
        log.info("================================= NettyUploadHandler.channelRead0 =====================================");
        if (httpObject instanceof HttpRequest) {
            this.request = (HttpRequest) httpObject;
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
                    readHttpDataChunkByChunk();
                    // 将内存中的数据序列化本地
                    log.info("======================= LastHttpContent =======================");
                    reset();
                    ResponseUtils.responseOK(ctx, new ResultInfo(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, "操作成功"));
                }
            }else{
                ResponseUtils.responseOK(ctx, new ResultInfo(ResultInfo.FAILURE, ResultInfo.MSG_FAILURE, "文件内容为空"));
            }
        }
    }

    private void readHttpDataChunkByChunk() throws IOException {
        log.info("readHttpDataChunkByChunk");
        while (decoder.hasNext()) {
            InterfaceHttpData data = decoder.next();
            log.info("data.getHttpDataType() === " + data.getHttpDataType());
            if (data != null && InterfaceHttpData.HttpDataType.FileUpload.equals(data.getHttpDataType())) {
                final FileUpload fileUpload = (FileUpload) data;
                final File file = new File(FILE_UPLOAD + fileUpload.getFilename());
                log.info("upload file: {}", file);
                try (FileChannel inputChannel = new FileInputStream(fileUpload.getFile()).getChannel();
                     FileChannel outputChannel = new FileOutputStream(file).getChannel()) {
                    outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
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

    private String getUploadResponse() {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Title</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<form action=\"http://127.0.0.1:8080/post_multipart\" enctype=\"multipart/form-data\" method=\"POST\">\n" +
                "\n" +
                "\n" +
                "    <input type=\"file\" name=" +
                " " +
                "" +
                "\"YOU_KEY\">\n" +
                "\n" +
                "    <input type=\"submit\" name=\"send\">\n" +
                "\n" +
                "</form>\n" +
                "\n" +
                "</body>\n" +
                "</html>";

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
