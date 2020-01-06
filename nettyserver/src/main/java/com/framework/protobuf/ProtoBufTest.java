package com.framework.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;

public class ProtoBufTest {

    public static void main(String[] args) throws InvalidProtocolBufferException {
        DateInfo.Student student = DateInfo.Student.newBuilder().setName("张三").setAge(20).setAddress("北京").build();

        byte[] bytes = student.toByteArray();

        DateInfo.Student student1 = DateInfo.Student.parseFrom(bytes);

        System.out.println(student1);
    }
}
