package org.example.iocexam.annotation;

import java.lang.reflect.Method;

// spring 에서 이런식으로 한다??
public class HelloRun {
    public static void main(String[] args) throws NoSuchMethodException {
        Hello hello = new Hello();

        Method method = hello.getClass().getDeclaredMethod("print");
        if (method.isAnnotationPresent(Count100.class)) {
            for (int i = 0; i < method.getAnnotation(Count100.class).value(); i++) {
                hello.print();
            }
        } else {
            hello.print();
        }
    }
}
