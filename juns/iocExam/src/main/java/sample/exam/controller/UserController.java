package sample.exam.controller;

import sample.exam.domain.User;
import sample.exam.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public void joinUser() {

        String name = "test";
        String password = "test1234";
        String email = "test@email.com";
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        user.setEmail(email);
        userService.joinUser(user);
    }
}
