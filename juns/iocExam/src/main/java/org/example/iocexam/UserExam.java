package org.example.iocexam;

import org.example.iocexam.config.ProductConfig;
import org.example.iocexam.config.UserConfig;
import org.example.iocexam.controller.ProductController;
import org.example.iocexam.controller.UserController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class UserExam {
    public static void main(String[] args) {
//        ApplicationContext context = new AnnotationConfigApplicationContext(UserConfig.class);
//
//        UserController userController = context.getBean("userController",  UserController.class);
//        userController.joinUser();

        ApplicationContext context = new AnnotationConfigApplicationContext(ProductConfig.class);
        ProductController productController = context.getBean("productController", ProductController.class);
        productController.save();

    }
}
