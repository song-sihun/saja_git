package org.diner.dinerreserve.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table("restaurant_tables")
public class RestaurantTable {
    @Id
    private Long id;
    private Long restaurantId;
    private String tableName;
    private int capacity;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
