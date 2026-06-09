package org.lion.springdatajps.springdatajpa2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    Optional<Customer> findByName(String name);
    Optional<Customer> findByEmail(String email);
    List<Customer> findByEmailContaining(String email);

    @Query("select c from Customer c where c.age > (select avg(c2.age) from Customer c2)")
    List<Customer> findCustomersOlderThanAverage();

}
