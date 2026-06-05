package org.diner.dinerreserve.service;

import lombok.extern.slf4j.Slf4j;
import org.diner.dinerreserve.config.exception.CustomException;
import org.diner.dinerreserve.config.status.RestaurantCategory;
import org.diner.dinerreserve.config.status.Role;
import org.diner.dinerreserve.config.status.UserStatus;
import org.diner.dinerreserve.domain.*;
import org.diner.dinerreserve.dto.reservation.ReservationRequest;
import org.diner.dinerreserve.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;


@Slf4j
@SpringBootTest
@Transactional
class ReservationServiceTest {

    @Autowired
    ReservationService reservationService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    RestaurantTableRepository restaurantTableRepository;

    @Autowired
    ReservationSlotRepository reservationSlotRepository;

    @Autowired
    ReservationRepository reservationRepository;

    User user;
    Restaurant restaurant;
    ReservationSlot slot;

    @BeforeEach
    void setUp() {
        LocalDateTime now = LocalDateTime.now();
        String testId = UUID.randomUUID().toString().substring(0, 8);

        user = new User();
        user.setEmail("test-" + testId + "@test.com");
        user.setPassword("1234");
        user.setName("테스트유저");
        user.setPhoneNumber("01011112222");
        user.setPhoneNumber("010" + String.format("%08d", Math.floorMod(testId.hashCode(), 100_000_000)));
        user.setRole(Role.USER);
        user.setStatus(UserStatus.ACTIVE);
        user.setCreatedAt(now);
        user.setUpdatedAt(now);
        userRepository.save(user);

        restaurant = new Restaurant();
        restaurant.setName("테스트 식당");
        restaurant.setAddress("서울시 테스트구");
        restaurant.setPhoneNumber("0211112222");
        restaurant.setDescription("테스트 식당입니다.");
        restaurant.setOpeningTime(LocalTime.of(11, 0));
        restaurant.setClosingTime(LocalTime.of(22, 0));
        restaurant.setCategory(RestaurantCategory.CAFE);
        restaurant.setActive(true);
        restaurant.setCreatedAt(now);
        restaurant.setUpdatedAt(now);
        restaurant.setName("test-restaurant-" + testId);
        restaurant.setAddress("test-address-" + testId);
        restaurantRepository.save(restaurant);

        RestaurantTable table2 = new RestaurantTable();
        table2.setRestaurantId(restaurant.getId());
        table2.setTableName("2인석");
        table2.setCapacity(2);
        table2.setActive(true);
        table2.setCreatedAt(now);
        table2.setUpdatedAt(now);
        restaurantTableRepository.save(table2);

        RestaurantTable table4 = new RestaurantTable();
        table4.setRestaurantId(restaurant.getId());
        table4.setTableName("4인석");
        table4.setCapacity(4);
        table4.setActive(true);
        table4.setCreatedAt(now);
        table4.setUpdatedAt(now);
        restaurantTableRepository.save(table4);

        RestaurantTable table6 = new RestaurantTable();
        table6.setRestaurantId(restaurant.getId());
        table6.setTableName("6인석");
        table6.setCapacity(6);
        table6.setActive(true);
        table6.setCreatedAt(now);
        table6.setUpdatedAt(now);
        restaurantTableRepository.save(table6);

        slot = new ReservationSlot();
        slot.setRestaurantId(restaurant.getId());
        slot.setSlotTime(LocalTime.of(18, 0));
        slot.setActive(true);
        slot.setCreatedAt(now);
        slot.setUpdatedAt(now);
        reservationSlotRepository.save(slot);
    }

    @Test
    void reserve_success() {
        // given
        ReservationRequest request = new ReservationRequest();
        request.setSlotId(slot.getId());
        request.setReservationDate(LocalDate.now().plusDays(1));
        request.setPartySize(2);
        request.setRequestMessage("창가 자리 부탁드립니다.");

        // when
        reservationService.reserve(user.getId(), restaurant.getId(), request);

        // then
        Iterable<Reservation> reservations = reservationRepository.findAll();

        for (Reservation reservation : reservations) {
            log.info(reservation.toString());
        }

    }

    @Test
    void reserve_select_smallest_available_table() {
        // given
        ReservationRequest request = new ReservationRequest();
        request.setSlotId(slot.getId());
        request.setReservationDate(LocalDate.now().plusDays(1));
        request.setPartySize(3);

        // when
        reservationService.reserve(user.getId(), restaurant.getId(), request);

        // then
        Iterable<Reservation> reservationAll = reservationRepository.findAll();
        for (Reservation reservation : reservationAll) {
            RestaurantTable selectedTable = restaurantTableRepository.findById(reservation.getTableId())
                    .orElseThrow();
            log.info(selectedTable.toString());
        }
    }

    @Test
    void reserve_fail_when_restaurant_inactive() {
        // given
        restaurant.setActive(false);
        restaurantRepository.save(restaurant);

        ReservationRequest request = new ReservationRequest();
        request.setSlotId(slot.getId());
        request.setReservationDate(LocalDate.now().plusDays(1));
        request.setPartySize(2);

        // when & then
        assertThatThrownBy(() -> reservationService.reserve(user.getId(), restaurant.getId(), request))
                .isInstanceOf(CustomException.class)
                .hasMessage("Restaurant not active");
    }

    @Test
    void reserve_fail_when_slot_inactive() {
        // given
        slot.setActive(false);
        reservationSlotRepository.save(slot);

        ReservationRequest request = new ReservationRequest();
        request.setSlotId(slot.getId());
        request.setReservationDate(LocalDate.now().plusDays(1));
        request.setPartySize(2);

        // when & then
        assertThatThrownBy(() -> reservationService.reserve(user.getId(), restaurant.getId(), request))
                .isInstanceOf(CustomException.class)
                .hasMessage("Slot not active");
    }

    @Test
    void reserve_fail_when_party_size_too_large() {
        // given
        ReservationRequest request = new ReservationRequest();
        request.setSlotId(slot.getId());
        request.setReservationDate(LocalDate.now().plusDays(1));
        request.setPartySize(10);

        // when & then
        assertThatThrownBy(() -> reservationService.reserve(user.getId(), restaurant.getId(), request))
                .isInstanceOf(CustomException.class)
                .hasMessage("Available table not found");
    }

    @Test
    void reserve_fail_when_slot_does_not_belong_to_restaurant() {
        // given
        String testId = UUID.randomUUID().toString().substring(0, 8);
        Restaurant otherRestaurant = new Restaurant();
        otherRestaurant.setName("다른 식당");
        otherRestaurant.setAddress("서울시 다른구");
        otherRestaurant.setPhoneNumber("0233334444");
        otherRestaurant.setDescription("다른 식당입니다.");
        otherRestaurant.setOpeningTime(LocalTime.of(11, 0));
        otherRestaurant.setClosingTime(LocalTime.of(22, 0));
        otherRestaurant.setCategory(RestaurantCategory.CAFE); // 네 enum에 맞게 수정
        otherRestaurant.setActive(true);
        otherRestaurant.setCreatedAt(LocalDateTime.now());
        otherRestaurant.setUpdatedAt(LocalDateTime.now());
        otherRestaurant.setName("other-restaurant-" + testId);
        otherRestaurant.setAddress("other-address-" + testId);
        restaurantRepository.save(otherRestaurant);

        ReservationRequest request = new ReservationRequest();
        request.setSlotId(slot.getId());
        request.setReservationDate(LocalDate.now().plusDays(1));
        request.setPartySize(2);

        // when & then
        assertThatThrownBy(() -> reservationService.reserve(user.getId(), otherRestaurant.getId(), request))
                .isInstanceOf(CustomException.class)
                .hasMessage("This slot does not belong to the restaurant");
    }
}
