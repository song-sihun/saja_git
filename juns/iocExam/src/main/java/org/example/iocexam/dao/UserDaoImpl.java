package org.example.iocexam.dao;

import org.example.iocexam.domain.User;

import java.util.List;

public class UserDaoImpl implements UserDao {
    @Override
    public User getUserByName(String username) {

        return null;
    }

    @Override
    public User getUserByEmail(String email) {
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return List.of();
    }

    @Override
    public void saveUser(User user) {
        System.out.println(user+" saved");

    }
}
