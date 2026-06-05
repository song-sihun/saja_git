package org.diner.dinerreserve.dto.restaurant;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.diner.dinerreserve.config.status.RestaurantCategory;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantCreateRequest {
    @NotBlank(message = "식당명은 필수입니다.")
    private String name;

    @NotBlank(message = "주소는 필수입니다.")
    private String address;

    @NotBlank(message = "전화번호는 필수입니다.")
    @Pattern(
            regexp = "^\\d{2,3}-?\\d{3,4}-?\\d{4}$",
            message = "전화번호 형식이 올바르지 않습니다."
    )
    private String phoneNumber;

    private String description;

    @NotNull(message = "카테고리는 필수입니다.")
    private RestaurantCategory category;
    @DateTimeFormat(pattern = "HH:mm")
    @NotNull(message = "오픈 시간은 필수입니다.")
    private LocalTime openingTime;

    @DateTimeFormat(pattern = "HH:mm")
    @NotNull(message = "마감 시간은 필수입니다.")
    private LocalTime closingTime;

}