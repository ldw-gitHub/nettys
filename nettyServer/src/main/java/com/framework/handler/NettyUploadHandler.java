package com.framework.handler;

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
import java.io.IOException;
import java.net.URI;

/**
 * @description
 * @author: liudawei
 * @date: 2020/1/19 15:56
 */
@Slf4j
public class NettyUploadHandler extends SimpleChannelInboundHandler<HttpObject> {


    private HttpRequest request;
    private static final String uploadUrl = "/up";
    private static final String fromFileUrl = "/post_multipart";
    private static final HttpDataFactory factory = new DefaultHttpDataFactory(DefaultHttpDataFactory.MINSIZE); // Disk if size exceed
    private HttpPostRequestDecoder decoder;

    static {
        DiskFileUpload.deleteOnExitTemporaryFile = true; // should delete file
        // on exit (in normal
        DiskFileUpload.baseDirectory = null; // system temp directory
        DiskAttribute.deleteOnExitTemporaryFile = true; // should delete file on
        // exit (in normal exit)
        DiskAttribute.baseDirectory = null; // system temp directory
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        if (decoder != null) {
            decoder.cleanFiles();
        }
    }

    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        if (msg instanceof HttpRequest) {
            this.request = (HttpRequest) msg;
//            decoder = new HttpPostRequestDecoder(factory, request);
            //DefaultHttpHeaders[Host: 127.0.0.1:8080,
            // Connection: keep-alive, Content-Length: 293525778, Cache-Control: max-age=0,
            // Origin: http://localhost:8080, Upgrade-Insecure-Requests: 1, Content-Type: multipart/form-data; boundary=----WebKitFormBoundarycHYbiRxp0ssOCkPS,
            // User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36,
            // Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3,
            // Referer: http://localhost:8080/up, Accept-Encoding: gzip, deflate, br, Accept-Language: zh-CN,zh;q=0.9,en;q=0.8]
            ///post_multipart

            //DefaultHttpHeaders[Host: localhost:8080, Connection: keep-alive, Content-Length: 904,
            // User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36,
            // Cache-Control: no-cache, Origin: chrome-extension://fhbjgbiflinjbdggehcddcbncdddomop,
            // Postman-Token: 91ee32a5-c8d7-4003-e74f-d341c4824c05,
            // Content-Type: multipart/form-data, Accept: */*, Accept-Encoding: gzip, deflate, br, Accept-Language: zh-CN,zh;q=0.9,en;q=0.8]
            ///post_multipart
            log.info(request.headers().toString());
            URI uri = new URI(request.uri());
            System.out.println(uri);
            urlRoute(ctx, uri.getPath());
        }

        if (decoder != null) {
            if (msg instanceof HttpContent) {
                // 接收一个新的请求体
                decoder.offer((HttpContent) msg);
                // 将内存中的数据序列化本地
                readHttpDataChunkByChunk();
            }

            if (msg instanceof LastHttpContent) {
                System.out.println("LastHttpContent");
                reset();
                writeResponse(ctx, "<h1>上传成功</h1>");
            }

        }


    }

    // url路由
    private void urlRoute(ChannelHandlerContext ctx, String uri) {
        StringBuilder urlResponse = new StringBuilder();
        // 访问文件上传页面
        if (uri.startsWith(uploadUrl)) {
            urlResponse.append(getUploadResponse());
        } else if (uri.startsWith(fromFileUrl)) {
            decoder = new HttpPostRequestDecoder(factory, request);
            return;
        } else {
            urlResponse.append(getHomeResponse());
        }
        writeResponse(ctx, urlResponse.toString());
    }

    private void writeResponse(ChannelHandlerContext ctx, String context) {
        ByteBuf buf = Unpooled.copiedBuffer(context, CharsetUtil.UTF_8);
        FullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1, HttpResponseStatus.OK, buf);
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html;charset=utf-8");
        //设置短连接 addListener 写完马上关闭连接
        ctx.channel().writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    private String getHomeResponse() {
        return " <h1> welcome home </h1> ";
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


    private void readHttpDataChunkByChunk() throws IOException {
        while (decoder.hasNext()) {
            InterfaceHttpData data = decoder.next();
            if (data != null) {
                log.info("data.getHttpDataType() --- " + data.getHttpDataType());
                if (data.getHttpDataType() == InterfaceHttpData.HttpDataType.FileUpload) {
                    FileUpload fileUpload = (FileUpload) data;
                    if (fileUpload.isCompleted()) {
                        fileUpload.isInMemory();// tells if the file is in Memory
                        // or on File
                        fileUpload.renameTo(new File("" + fileUpload.getFilename())); // enable to move into another
                        // File dest
                        decoder.removeHttpDataFromClean(fileUpload); //remove
                    }
                }else if(data.getHttpDataType() == InterfaceHttpData.HttpDataType.Attribute){
                    Attribute attribute = (Attribute) data;
                    String name = attribute.getName();
                    String value = attribute.getValue();
                    log.info("name --- " + name);
                    log.info("value --- " + value);

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
}
