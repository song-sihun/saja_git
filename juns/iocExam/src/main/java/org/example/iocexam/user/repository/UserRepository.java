package org.example.iocexam.user.repository;

import org.example.iocexam.user.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository {
    User findById(int id);
    List<User> findAll();
    List<User> findByName(String name);
    List<User> findByRole(String role);
    List<User> findByEmail(String email);
    List<User> findByAddress(String address);

    void deleteById(int id);
    void save(User user);

}
