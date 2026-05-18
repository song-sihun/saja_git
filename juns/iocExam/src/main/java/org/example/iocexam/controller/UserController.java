package org.example.iocexam.controller;

import org.example.iocexam.domain.User;
import org.example.iocexam.service.UserService;

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
