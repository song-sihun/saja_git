package org.diner.dinerreserve.dto.reservation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.diner.dinerreserve.config.status.ReservationStatus;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
public class MyReservationResponse {
    private Long reservationId;
    private String restaurantName;
    private String restaurantAddress;
    private Long tableId;
    private Long slotId;
    private LocalTime slotTime;
    private LocalDate reservationDate;
    private Integer partySize;
    private ReservationStatus status;
    private String requestMessage;
}