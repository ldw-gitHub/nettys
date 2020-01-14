package com.framework.decorator;

public class ConcreateDecorator2 extends Decorator{
    public ConcreateDecorator2(Component component) {
        super(component);
    }

    @Override
    public void doSomething() {
        super.doSomething();
        this.doAnotherThing();
    }

    public void doAnotherThing(){
        System.out.println("功能b");
    }
}
