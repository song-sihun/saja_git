package org.example.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "persons")
@Getter @Setter
@NoArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;

    @OneToOne(mappedBy = "person", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private Passport passport;

    public Person(String name) {
        this.name = name;
    }

    public void setPassport(Passport passport) {
        this.passport = passport;
        if (passport != null) {
            passport.setPerson(this);
        }
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
