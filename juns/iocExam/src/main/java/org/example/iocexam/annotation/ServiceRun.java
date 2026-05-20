package org.example.iocexam.annotation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ServiceRun {
    public static void main(String[] args) {

        Service service = new Service();

        Method[] declaredMethods = service.getClass().getDeclaredMethods();

        for (Method method : declaredMethods) {
            if(method.isAnnotationPresent(PrintAnnotation.class)){
                PrintAnnotation printAnnotation = method.getAnnotation(PrintAnnotation.class);
                System.out.println(printAnnotation.number());
                System.out.println(printAnnotation.value());
            }
            try {
                method.invoke(service);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
