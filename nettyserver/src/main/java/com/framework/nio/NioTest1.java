package com.framework.nio;

import java.nio.IntBuffer;
import java.security.SecureRandom;

public class NioTest1 {
    public static void main(String[] args) {
        IntBuffer buffer = IntBuffer.allocate(10);

        for (int i = 0;i < buffer.capacity(); i++){
            int randomNumber = new SecureRandom().nextInt(20);
            buffer.put(randomNumber);
        }

        buffer.flip();//状态翻转、先是读存数据，下面是写出数据

        while(buffer.hasRemaining()){
            System.out.println(buffer.get());
        }
    }
}
