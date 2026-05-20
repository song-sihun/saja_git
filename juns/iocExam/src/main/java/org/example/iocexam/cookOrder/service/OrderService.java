package org.example.iocexam.cookOrder.service;

import org.example.iocexam.cookOrder.cook.Cook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class OrderService implements CookService{
    Logger logger = LoggerFactory.getLogger(OrderService.class);


    public OrderService() {
        logger.info("주문이 접수되었습니다.");
    }

    @Override
    public void order(Cook cook) {
        cook.cook();
    }
}
