package org.diner.dinerreserve.service;

import org.diner.dinerreserve.config.status.RestaurantCategory;
import org.diner.dinerreserve.dto.restaurant.RestaurantCreateRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;

@SpringBootTest
@Transactional
class RestaurantServiceTest {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserService userService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void register() {

        RestaurantCreateRequest restaurantCreateRequest = new RestaurantCreateRequest();

        restaurantCreateRequest.setName("Restaurant");
        restaurantCreateRequest.setAddress("address");
        restaurantCreateRequest.setCategory(RestaurantCategory.CAFE);
        restaurantCreateRequest.setPhoneNumber("1234567890");

        restaurantCreateRequest.setOpeningTime(LocalTime.of(12, 0));
        restaurantCreateRequest.setClosingTime(LocalTime.of(23, 0));

        restaurantCreateRequest.setDescription("description");

        restaurantService.register(restaurantCreateRequest);

    }

    @Test
    void findAll() {
    }

    @Test
    void findByNameContaining() {
    }
}