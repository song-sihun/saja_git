package org.example.jpa.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("TRUCK")
@Getter
@Setter
@NoArgsConstructor
public class Truck extends Vehicle {
    private double payloadCapacity;

    public Truck(double payloadCapacity) {
        this.payloadCapacity = payloadCapacity;
    }
}
