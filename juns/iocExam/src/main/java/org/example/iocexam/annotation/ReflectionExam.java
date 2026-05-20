package org.example.iocexam.annotation;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionExam {
    public static void main(String[] args) throws Exception {
        // 객체 생성해줌
        Class<?> personClass = Class.forName("org.example.iocexam.annotation.Person");
        Object personInstance = personClass.getDeclaredConstructor().newInstance();

        Field nameField = personClass.getDeclaredField("name");
        nameField.setAccessible(true); // private field에 접근 가능
        nameField.set(personInstance, "test");

        Method method = personClass.getDeclaredMethod("sayHello");
        method.setAccessible(true);
        method.invoke(personInstance);



    }
}
