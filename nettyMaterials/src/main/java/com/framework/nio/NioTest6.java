package com.framework.nio;

import java.nio.ByteBuffer;

/**
 * 分片 slice buffer 只是引用，修改会修改源数据
 */
public class NioTest6 {
    public static void main(String[] args) {

        ByteBuffer buffer = ByteBuffer.allocate(10);

        for (int i = 0; i <buffer.capacity();i++){
            buffer.put((byte) i);
        }

        buffer.position(2);
        buffer.limit(6);

        ByteBuffer slice = buffer.slice();

        for (int i = 0; i < slice.capacity();i++){
            byte b = slice.get(i);
            b *= 2;
            slice.put(i,b);
        }

        buffer.position(0);
        buffer.limit(buffer.capacity());

        for (int i = 0;i < buffer.capacity();i++)
            System.out.println(buffer.get(i));


    }
}
