package com.framework.rpc.protobuf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Random;

public class TestClientHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MyDataInfo.MyMessage myMessage) throws Exception {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        int randomInt = new Random().nextInt(3);

        MyDataInfo.MyMessage myMessage = null;
        if(0 == randomInt){//person
            myMessage = MyDataInfo.MyMessage.newBuilder().setDataType(MyDataInfo.MyMessage.DataType.PersonType).setPerson(
                    MyDataInfo.Person.newBuilder().setName("张三").setAge(20).setAddress("北京").build()
            ).build();
        }else if(1 == randomInt){
            myMessage = MyDataInfo.MyMessage.newBuilder().setDataType(MyDataInfo.MyMessage.DataType.PersonType).setDog(
                    MyDataInfo.Dog.newBuilder().setName("一只狗").setAge(2).build()
            ).build();
        }else{
            myMessage = MyDataInfo.MyMessage.newBuilder().setDataType(MyDataInfo.MyMessage.DataType.PersonType).setCat(
                    MyDataInfo.Cat.newBuilder().setName("一只猫").setCity("武汉").build()
            ).build();
        }

        ctx.channel().writeAndFlush(myMessage);

    }
}
