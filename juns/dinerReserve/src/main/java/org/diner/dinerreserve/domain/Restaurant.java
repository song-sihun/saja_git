package org.diner.dinerreserve.domain;

import lombok.*;
import org.diner.dinerreserve.config.status.RestaurantCategory;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table("restaurants")
public class Restaurant {

    @Id
    private Long id;
    private String name;
    private String address;
    private String phoneNumber;
    private String description;
    private RestaurantCategory category;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void deactivate(){
        this.active = false;
    }

    public void activate(){
        this.active = true;
    }
}
