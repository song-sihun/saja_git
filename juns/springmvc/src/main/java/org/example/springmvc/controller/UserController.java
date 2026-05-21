package org.example.springmvc.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.springmvc.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/users")
public class UserController {
    @GetMapping("/join")
    public String joinPage() {
        return "user_join_form";
    }

//    @RequestParam(value = "name") String name, @RequestParam(value = "password") String password, @RequestParam(value = "email") String email
    @PostMapping("/join")
    public String join(@ModelAttribute User user) {
        log.info("name::{}", user.getName());
        log.info("password::{}", user.getPassword());
        log.info("email::{}", user.getEmail());
        return "redirect:/hi";
    }

    @GetMapping("/all-users")
    public String allUsersPage(Model model) {
        List<User> userList = Arrays.asList(
                new User("jun1", "1234", "test1@email.com"),
                new User("jun2", "1234", "test2@email.com"),
                new User("jun3", "1234", "test3@email.com"),
                new User("jun4", "1234", "test4@email.com")

        );
        model.addAttribute("users", userList);
        return "user_list";
    }
}
