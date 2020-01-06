package com.framework.decorator;

public class ConcreateComponent implements Component {
    @Override
    public void doSomething() {
        System.out.println("功能A");
    }
}
