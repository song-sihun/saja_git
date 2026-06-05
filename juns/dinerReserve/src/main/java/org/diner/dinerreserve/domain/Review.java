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
@Table("reviews")
public class Review {
    @Id
    private Long id;
    private Long restaurantId;
    private Long reservationId;
    private Long userId;
    private Double rating;
    private String content;
    private boolean visible;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
