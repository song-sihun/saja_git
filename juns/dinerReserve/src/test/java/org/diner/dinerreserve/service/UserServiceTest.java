package org.diner.dinerreserve.service;

import org.diner.dinerreserve.dto.user.UserCreateRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findById() {
    }

    @Test
    void findByStatus() {
    }

    @Test
    void findByRole() {
    }

    @Test
    void getUserByEmail() {
    }

    @Test
    void findAllPage() {
    }

    @Test
    void save() {
    }

    @Test
    void delete() {
    }

    @Test
    void update() {
    }

    @Test
    void join() {
        UserCreateRequest user =  new UserCreateRequest(
                "email@email.com",
                "testPassword!@#$",
                "testPassword!@#$",
                "name",
                "01012341234"

        );
        userService.join(user);
    }

    @Test
    void login() {
    }

    @Test
    void changePassword() {
    }
}