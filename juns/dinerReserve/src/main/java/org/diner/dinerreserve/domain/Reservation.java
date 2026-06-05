package org.diner.dinerreserve.domain;

import lombok.*;
import org.diner.dinerreserve.config.status.ReservationStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table("reservations")
public class Reservation {
    @Id
    private Long id;
    private Long restaurantId;
    private Long tableId;
    private Long userId;
    private Long slotId;

    private LocalDate reservationDate;
    private int partySize;
    private String requestMessage;
    private ReservationStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime cancelledAt;

    public void cancel() {
        if (this.status == ReservationStatus.CANCELLED) {
            throw new IllegalArgumentException("이미 취소된 예약입니다.");
        }

        if (this.status == ReservationStatus.COMPLETED || this.status == ReservationStatus.NO_SHOW) {
            throw new IllegalArgumentException("완료 또는 노쇼 예약은 취소할 수 없습니다.");
        }

        this.status = ReservationStatus.CANCELLED;
        this.cancelledAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
