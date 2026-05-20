package org.example.iocexam.annotation;

public class Service {
    @PrintAnnotation(number = 1, value = "+")
    public void print() {
        System.out.println("Hello Service");
    }
}
