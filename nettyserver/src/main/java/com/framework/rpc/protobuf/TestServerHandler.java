package com.framework.rpc.protobuf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class TestServerHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MyDataInfo.MyMessage myMessage) throws Exception {
        MyDataInfo.MyMessage.DataType dataType = myMessage.getDataType();

        if (dataType == MyDataInfo.MyMessage.DataType.PersonType){
            MyDataInfo.Person person = myMessage.getPerson();
            System.out.println(person.getName() + person.getAddress() + person.getAge());

        }else if(dataType == MyDataInfo.MyMessage.DataType.DogType){
            MyDataInfo.Dog dog = myMessage.getDog();
            System.out.println(dog.getName() + dog.getAge());
        }else{
            MyDataInfo.Cat cat = myMessage.getCat();
            System.out.println(cat.getName() + cat.getCity());
        }

    }
}
