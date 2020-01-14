package com.framework.zerocopy;

import javax.sound.midi.Soundbank;
import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * @description
 * @author: liudawei
 * @date: 2020/1/9 16:51
 */
public class NewIOClient {
    public static void main(String[] args) throws Exception{
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress(8899));
        socketChannel.configureBlocking(false);

        String fileName = "C:\\Users\\20190322\\Desktop\\mobile.xls";

        FileChannel fileChannel = new FileInputStream(fileName).getChannel();

        long startTime = System.currentTimeMillis();

        long transferTo = fileChannel.transferTo(0,fileChannel.size(),socketChannel);//零拷贝

        System.out.println("发送总字节数： " + transferTo + ", 总耗时 : " + (System.currentTimeMillis() - startTime));

    }
}
