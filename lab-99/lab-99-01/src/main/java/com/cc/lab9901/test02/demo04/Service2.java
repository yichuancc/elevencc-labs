package com.cc.lab9901.test02.demo04;

/**
 * @program: elevencc-labs
 * @description:
 * @author: yic
 * @create: 2024-06-06 21:43
 */
public class Service2 {

    private Service1 service1;

    public Service1 getService1() {
        return service1;
    }

    public void setService1(Service1 service1) {
        this.service1 = service1;
    }

    @Override
    public String toString() {
        return "Service2{" +
                "service1=" + service1 +
                '}';
    }
}
