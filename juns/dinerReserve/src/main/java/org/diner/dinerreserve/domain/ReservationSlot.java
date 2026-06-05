package org.diner.dinerreserve.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table("reservation_slots")
public class ReservationSlot {

    @Id
    private Long id;
    private Long restaurantId;
    private LocalTime slotTime;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
