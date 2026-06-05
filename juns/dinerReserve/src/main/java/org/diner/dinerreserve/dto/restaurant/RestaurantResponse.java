package org.diner.dinerreserve.dto.restaurant;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.diner.dinerreserve.config.status.RestaurantCategory;
import org.diner.dinerreserve.domain.Restaurant;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantResponse {
    private Long id;
    private String name;
    private String address;
    private String phoneNumber;
    private String description;
    private boolean active;
    private RestaurantCategory category;
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime openingTime;
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime closingTime;

    public RestaurantResponse(Restaurant restaurant) {
         this.id = restaurant.getId();
         this.name = restaurant.getName();
         this.address = restaurant.getAddress();
         this.description = restaurant.getDescription();
         this.category = restaurant.getCategory();
         this.active = restaurant.isActive();
         this.openingTime = restaurant.getOpeningTime();
         this.closingTime = restaurant.getClosingTime();
         this.phoneNumber = formatPhone(restaurant.getPhoneNumber());
    }


    private String formatPhone(String phone) {
       if (phone == null) return "";
       if (phone.startsWith("02") && phone.length() == 10) {
           return phone.replaceFirst("(\\d{2})(\\d{4})(\\d+)", "$1-$2-$3");
       }
       if (phone.startsWith("02") && phone.length() == 9) {
           return phone.replaceFirst("(\\d{2})(\\d{3})(\\d+)", "$1-$2-$3");
       }
       if (phone.length() == 11) {
           return phone.replaceFirst("(\\d{3})(\\d{4})(\\d+)", "$1-$2-$3");
       } else if (phone.length() == 10) {
           return phone.replaceFirst("(\\d{3})(\\d{3})(\\d+)", "$1-$2-$3");
       }
       return phone;
    }
}
