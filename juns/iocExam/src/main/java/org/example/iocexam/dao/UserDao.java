package org.example.iocexam.dao;

import org.example.iocexam.domain.User;

import java.util.List;

public interface UserDao {
    public User getUserByName(String username);
    public User getUserByEmail(String email);
    public List<User> getAllUsers();
    public void saveUser(User user);
}
