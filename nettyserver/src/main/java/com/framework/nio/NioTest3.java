package com.framework.nio;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioTest3 {
    /**
     * nio写数据
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        FileOutputStream fileOutputStream = new FileOutputStream("NioTest3.txt");

        FileChannel channel = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(512);

        byte[] message = "hello world welcome ".getBytes();
        for (int i = 0; i < message.length;i++){
            byteBuffer.put(message[i]);
        }

        byteBuffer.flip();

        channel.write(byteBuffer);

        fileOutputStream.close();
    }
}
