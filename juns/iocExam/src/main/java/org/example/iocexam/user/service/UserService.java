package org.example.iocexam.user.service;

import org.example.iocexam.user.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    void save(User user);

    List<User> findAll();
    User findById(int id);
    List<User> findByName(String name);

    void deleteById(int id);
}
