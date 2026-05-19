package org.example.iocexam.cookOrder.service;

import org.example.iocexam.cookOrder.cook.Cook;
import org.example.iocexam.cookOrder.cook.OrderLogger;

public class OrderService implements CookService{

    private final OrderLogger orderLogger;

    public OrderService(OrderLogger orderLogger) {
        this.orderLogger = orderLogger;
    }

    @Override
    public void order(Cook cook) {
        orderLogger.start();
        cook.cook();
        orderLogger.end();
    }
}
