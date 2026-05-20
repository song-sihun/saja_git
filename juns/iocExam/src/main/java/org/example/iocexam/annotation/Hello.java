package org.example.iocexam.annotation;


public class Hello {
    private String message;

    @Count100(5)
    public void print(){
        System.out.println("Hello");
    }
}
