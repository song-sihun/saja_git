package org.lion.springdatajps.springdatajpa2;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;



    public Customer findById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    public Customer findByName(String name) {
        return customerRepository.findByName(name).orElse(null);
    }

    @Transactional
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Transactional
    public Customer updateCustomer(Long id, Customer customerInfo) {

        Customer customer = findById(id);
        customer.setName(customerInfo.getName());
        customer.setAge(customerInfo.getAge());
        customer.setEmail(customerInfo.getEmail());

        return customer;
    }

    @Transactional
    public void  deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    @Transactional
    public Order createOrder(Order order, Customer customer) {
        order.setCustomer(customer);
        return order;
    }

    public int getOrdersCount(Long customerId) {
        Customer customer = findById(customerId);
        return customer.getOrderCount();
    }

    public Optional<Order> getLastOrder(Long customerId) {
        Customer customer = findById(customerId);
        return customer.getLastOrder();
    }

    public List<Customer> findCustomersOlderThanAverage() {
        return customerRepository.findCustomersOlderThanAverage();
    }


}
