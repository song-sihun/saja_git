package org.example.jpa.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "passports")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Passport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "passport_number", unique = true)
    private String passportNumber;

    @OneToOne
    @JoinColumn(name = "person_id", unique = true)
    private Person person;

    public Passport(String passportNumber) {
        this.passportNumber = passportNumber;
    }

}
