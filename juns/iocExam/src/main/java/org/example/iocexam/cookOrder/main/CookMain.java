package org.example.iocexam.cookOrder.main;

import org.example.iocexam.cookOrder.config.CookConfig;
import org.example.iocexam.cookOrder.cook.Cook;
import org.example.iocexam.cookOrder.service.CookService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Import;


@SpringBootApplication(scanBasePackages = "org.example.iocexam.cookOrder")
//@SpringBootApplication
//@Import(CookConfig.class)
public class CookMain {

    public static void main(String[] args) {

        ApplicationContext ctx = SpringApplication.run(CookMain.class, args);

//        ApplicationContext ctx =
//                new AnnotationConfigApplicationContext(CookConfig.class);
        // Config에 @ComponentScan(basePackages = "org.example.iocexam.cookOrder") 있어야 됨

        CookService orderService = ctx.getBean(CookService.class);

        Cook cook = ctx.getBean("koreanCook", Cook.class);

        orderService.order(cook);

    }

}
