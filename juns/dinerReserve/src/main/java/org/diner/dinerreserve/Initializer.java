package org.diner.dinerreserve;

import lombok.RequiredArgsConstructor;
import org.diner.dinerreserve.config.status.RestaurantCategory;
import org.diner.dinerreserve.config.status.Role;
import org.diner.dinerreserve.config.status.UserStatus;
import org.diner.dinerreserve.domain.ReservationSlot;
import org.diner.dinerreserve.domain.Restaurant;
import org.diner.dinerreserve.domain.RestaurantTable;
import org.diner.dinerreserve.domain.User;
import org.diner.dinerreserve.repository.ReservationSlotRepository;
import org.diner.dinerreserve.repository.RestaurantRepository;
import org.diner.dinerreserve.repository.RestaurantTableRepository;
import org.diner.dinerreserve.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class Initializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final RestaurantTableRepository restaurantTableRepository;
    private final ReservationSlotRepository reservationSlotRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        String adminEmail = "admin@admin.com";

        if (userRepository.existsByEmail(adminEmail)) {
            return;
        }

        LocalDateTime now = LocalDateTime.now();

        // 관리자 계정
        User admin = new User();
        admin.setEmail(adminEmail);
        admin.setPassword(passwordEncoder.encode("admin1234!"));
        admin.setName("관리자");
        admin.setPhoneNumber("01000000000");
        admin.setRole(Role.ADMIN);
        admin.setStatus(UserStatus.ACTIVE);
        admin.setCreatedAt(now);
        admin.setUpdatedAt(now);
        userRepository.save(admin);

        // 일반 유저 계정
        User user = new User();
        user.setEmail("user@test.com");
        user.setPassword(passwordEncoder.encode("user1234!"));
        user.setName("테스트유저");
        user.setPhoneNumber("01011112222");
        user.setRole(Role.USER);
        user.setStatus(UserStatus.ACTIVE);
        user.setCreatedAt(now);
        user.setUpdatedAt(now);
        userRepository.save(user);

        // 식당 1
        Restaurant restaurant1 = new Restaurant();
        restaurant1.setName("테스트 한식당");
        restaurant1.setAddress("서울시 강남구 테스트로 1");
        restaurant1.setPhoneNumber("0211112222");
        restaurant1.setDescription("예약 테스트용 한식당입니다.");
        restaurant1.setOpeningTime(LocalTime.of(11, 0));
        restaurant1.setClosingTime(LocalTime.of(22, 0));
        restaurant1.setCategory(RestaurantCategory.KOREAN);
        restaurant1.setActive(true);
        restaurant1.setCreatedAt(now);
        restaurant1.setUpdatedAt(now);
        restaurantRepository.save(restaurant1);

        // 식당 2
        Restaurant restaurant2 = new Restaurant();
        restaurant2.setName("테스트 파스타집");
        restaurant2.setAddress("서울시 마포구 테스트로 2");
        restaurant2.setPhoneNumber("0233334444");
        restaurant2.setDescription("예약 테스트용 양식당입니다.");
        restaurant2.setOpeningTime(LocalTime.of(10, 0));
        restaurant2.setClosingTime(LocalTime.of(21, 0));
        restaurant2.setCategory(RestaurantCategory.WESTERN);
        restaurant2.setActive(true);
        restaurant2.setCreatedAt(now);
        restaurant2.setUpdatedAt(now);
        restaurantRepository.save(restaurant2);

        // 식당 1 테이블
        createTable(restaurant1.getId(), "1번 테이블", 2, now);
        createTable(restaurant1.getId(), "2번 테이블", 2, now);
        createTable(restaurant1.getId(), "3번 테이블", 4, now);
        createTable(restaurant1.getId(), "4번 테이블", 4, now);
        createTable(restaurant1.getId(), "5번 테이블", 6, now);

        // 식당 2 테이블
        createTable(restaurant2.getId(), "1번 테이블", 2, now);
        createTable(restaurant2.getId(), "2번 테이블", 4, now);
        createTable(restaurant2.getId(), "3번 테이블", 4, now);

        // 식당 1 예약 슬롯
        createSlot(restaurant1.getId(), LocalTime.of(12, 0), now);
        createSlot(restaurant1.getId(), LocalTime.of(13, 0), now);
        createSlot(restaurant1.getId(), LocalTime.of(18, 0), now);
        createSlot(restaurant1.getId(), LocalTime.of(19, 0), now);
        createSlot(restaurant1.getId(), LocalTime.of(20, 0), now);

        // 식당 2 예약 슬롯
        createSlot(restaurant2.getId(), LocalTime.of(12, 0), now);
        createSlot(restaurant2.getId(), LocalTime.of(18, 0), now);
        createSlot(restaurant2.getId(), LocalTime.of(19, 0), now);
        createSlot(restaurant2.getId(), LocalTime.of(20, 0), now);
    }

    private void createTable(Long restaurantId, String name, int capacity, LocalDateTime now) {
        RestaurantTable table = new RestaurantTable();
        table.setRestaurantId(restaurantId);
        table.setTableName(name);
        table.setCapacity(capacity);
        table.setActive(true);
        table.setCreatedAt(now);
        table.setUpdatedAt(now);

        restaurantTableRepository.save(table);
    }

    private void createSlot(Long restaurantId, LocalTime slotTime, LocalDateTime now) {
        ReservationSlot slot = new ReservationSlot();
        slot.setRestaurantId(restaurantId);
        slot.setSlotTime(slotTime);
        slot.setActive(true);
        slot.setCreatedAt(now);
        slot.setUpdatedAt(now);

        reservationSlotRepository.save(slot);
    }
}