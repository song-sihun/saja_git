package org.example.iocexam.config;

import org.example.iocexam.controller.UserController;
import org.example.iocexam.dao.UserDao;
import org.example.iocexam.dao.UserDaoImpl;
import org.example.iocexam.service.UserService;
import org.example.iocexam.service.UserServiceImpl;
import org.springframework.context.annotation.Bean;

public class UserConfig {

    @Bean
    public UserDao userDao() {
        return new UserDaoImpl();
    }

    @Bean
    public UserService userService(UserDao userDao) {
        return new UserServiceImpl(userDao);
    }

    @Bean
    public UserController userController(UserService userService) {
        return new UserController(userService);
    }
}
