package org.lion.springdatajps.springdatajpa2;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@Transactional
@SpringBootTest
class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @BeforeEach
    void setUp() {

        Customer customer = new Customer();
        customer.setName("test");
        customer.setEmail("test@test.com");
        customer.setAge(20);
        customerService.createCustomer(customer);


        Order order = new Order();
        order.setCustomer(customer);
        order.setDate(LocalDate.now());
        order.setProduct("Test Product");
        customerService.createOrder(order, customer);

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findById() {
    }

    @Test
    void findByName() {
    }

    @Test
    void createCustomer() {
    }

    @Test
    void updateCustomer() {
    }

    @Test
    void deleteCustomer() {
    }

    @Test
    void createOrder() {
    }

    @Test
    void getOrdersCount() {
    }

    @Test
    void getLastOrder() {
        Customer customer = customerService.findByName("test");
        log.info("{}", customer);
        Optional<Order> order = customerService.getLastOrder(customer.getId());
        log.info("Order: {}", order.toString());

    }

    @Test
    void findCustomersOlderThanAverage() {
    }
}