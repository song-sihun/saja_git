package org.example.iocexam.service;

import org.example.iocexam.dao.UserDao;
import org.example.iocexam.domain.User;

public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void joinUser(User user) {
        userDao.saveUser(user);
    }
}
