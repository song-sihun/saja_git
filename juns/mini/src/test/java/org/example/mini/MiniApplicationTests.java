package org.example.mini;

import lombok.extern.slf4j.Slf4j;
import org.example.mini.config.user.UserRole;
import org.example.mini.config.user.UserStatus;
import org.example.mini.roomReservation.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
@SpringBootTest
class MiniApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void userCreateTest() {
        // given
        String email = "test@test.com";
        String password = "encodedPassword";
        String username = "tester";

        // when
        User user = User.createUser(email, password, username);

        // then
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getPassword()).isEqualTo(password);
        assertThat(user.getUsername()).isEqualTo(username);
        assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(user.getRole()).isEqualTo(UserRole.USER);

        log.info("user created");
        log.info(user.toString());
    }

}
