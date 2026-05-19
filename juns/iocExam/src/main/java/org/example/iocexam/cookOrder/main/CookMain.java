package org.example.iocexam.cookOrder.main;

import org.example.iocexam.cookOrder.config.CookConfig;
import org.example.iocexam.cookOrder.cook.Cook;
import org.example.iocexam.cookOrder.service.CookService;
import org.example.iocexam.cookOrder.service.OrderService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;


//@SpringBootApplication(scanBasePackages = "org.example.iocexam.cookOrder")
@SpringBootApplication
@Import(CookConfig.class)
public class CookMain {

    public static void main(String[] args) {

        ApplicationContext ctx = SpringApplication.run(CookMain.class, args);

        CookService orderService = ctx.getBean(CookService.class);
        String userOrder = "chineseCook";
        Cook cook = ctx.getBean(userOrder, Cook.class);
        orderService.order(cook);

    }

}
