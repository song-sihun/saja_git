package org.diner.dinerreserve.dto.reservation;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRequest {
    @NotNull(message = "시간 선택은 필수입니다")
    private Long slotId;

    @NotNull(message = "예약 날짜는 필수입니다")
    @FutureOrPresent(message = "예약 날짜는 오늘 이후여야 합니다.")
    private LocalDate reservationDate;

    @Positive
    @NotNull(message = "예약 인원을 입력해야 합니다.")
    @Min(value = 1, message = "예약 인원은 1명 이상이어야 합니다.")
    @Max(value = 6, message = "예약 인원은 6명 이하이어야 합니다.")
    private Integer partySize;
    private String requestMessage;
}
