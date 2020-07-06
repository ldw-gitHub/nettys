package com.framework;

/**
 * @description
 * @author: liudawei
 * @date: 2020/6/22 14:43
 */
public class LockModel {
    public int a = 0;

    public void addA(int i){
        a += i;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }
}
