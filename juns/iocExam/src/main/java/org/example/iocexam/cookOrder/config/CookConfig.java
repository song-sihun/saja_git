package org.example.iocexam.cookOrder.config;

import org.example.iocexam.cookOrder.cook.*;
import org.example.iocexam.cookOrder.service.OrderService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CookConfig {
    @Bean
    public Cook koreanCook(){
        return new KoreanCook();
    }

    @Bean
    public Cook chineseCook(){
        return new ChineseCook();
    }

    @Bean
    public Cook japaneseCook(){
        return new JapaneseCook();
    }

    @Bean
    public OrderLogger orderLogger(){
        return new OrderLogger();
    }

    @Bean
    public OrderService orderService(OrderLogger orderLogger){
        return new OrderService(orderLogger);
    }
}
