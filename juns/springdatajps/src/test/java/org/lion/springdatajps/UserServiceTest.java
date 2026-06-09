package org.lion.springdatajps;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@Transactional
@SpringBootTest
class UserServiceTest {

    @Autowired // 스프링이 컨테이너에서 UserService 빈을 자동으로 주입해줍니다.
    private UserService userService;


    @BeforeEach
    void setUp() {

        userService.create(new User("carami","carami@gmail.com"));
        userService.create(new User("carami","carami2@gmail.com"));
        userService.create(new User("kang","kang@gmail.com"));
        userService.create(new User("kim","kim@gmail.com"));
        userService.create(new User("hong","hong@gmail.com"));
        userService.create(new User("lee","lee@gmail.com"));
        log.info("Set up");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void create() {
        User user = new User();
        user.setName("test");
        user.setEmail("test@email.com");
        userService.create(user);

    }

    @Test
    void findUserById() {
        User user = new User();
        user.setName("test");
        user.setEmail("test@email.com");
        User savedUser = userService.create(user);

        User findUser = userService.findUserById(savedUser.getId()).orElseThrow(()->new RuntimeException("User not found"));
        log.info(findUser.toString());
        assertEquals(user.getName(), findUser.getName());
        assertEquals(user.getEmail(), findUser.getEmail());

    }

    @Test
    void update() {
        User user = new User();
        user.setName("test");
        user.setEmail("test@email.com");
        User savedUser = userService.create(user);

        User findUser = userService.findUserById(savedUser.getId()).orElseThrow(()->new RuntimeException("User not found"));
        findUser.setName("test2");
        findUser.setEmail("test2@email.com");
        User updatedUser = userService.update(savedUser.getId(), findUser);

        assertEquals(findUser.getName(), updatedUser.getName());
        assertEquals(findUser.getEmail(), updatedUser.getEmail());


    }

    @Test
    void deleteById() {
    }


    @Test
    void findAllUsersByNameLike(){
        List<User> users = userService.findAllUsersByNameLike("%car%");
        log.info(users.toString());
    }


    @Test
    void findAllUsersByNameContaining(){
        List<User> users = userService.findAllUsersByNameContaining("car");
        log.info(users.toString());
    }
}