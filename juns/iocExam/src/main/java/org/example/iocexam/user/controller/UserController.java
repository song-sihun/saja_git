package org.example.iocexam.user.controller;

import org.example.iocexam.user.entity.User;
import org.example.iocexam.user.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable int id) {
        return userService.findById(id);
    }

    @PostMapping("/create")
    public void create(@RequestBody User user) {
        userService.save(user);
    }

}
