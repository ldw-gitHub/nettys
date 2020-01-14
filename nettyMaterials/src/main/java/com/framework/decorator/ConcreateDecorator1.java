package com.framework.decorator;

public class ConcreateDecorator1 extends Decorator {
    public ConcreateDecorator1(Component component) {
        super(component);
    }

    @Override
    public void doSomething() {
        super.doSomething();
        this.doAnotherThing();
    }

    private void doAnotherThing(){
        System.out.println("功能a");
    }
}
