package com.framework.decorator;

public class Client {

    public static void main(String[] args) {
        /**
         * 装饰模式
         */
        Component component = new ConcreateDecorator2(new ConcreateDecorator1(new ConcreateComponent()));
        component.doSomething();
    }
}
