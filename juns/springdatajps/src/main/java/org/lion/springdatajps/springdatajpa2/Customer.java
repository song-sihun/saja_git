package org.lion.springdatajps.springdatajpa2;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(unique = true)
    private String email;
    private int age;


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "customer")
    private List<Order> orders = new ArrayList<>();

    public Customer(String email, int age) {
        this.email = email;
        this.age = age;
    }

    public int getOrderCount() {
        return orders.size();
    }

    public Optional<Order> getLastOrder() {
        if (orders.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(orders.get(orders.size() - 1));
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", age=" + age +
                '}';
    }
}
