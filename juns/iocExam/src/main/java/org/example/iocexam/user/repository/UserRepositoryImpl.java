package org.example.iocexam.user.repository;

import org.example.iocexam.user.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {
    @Override
    public List<User> findAll() {
        System.out.println("All Users");
        return List.of();
    }

    @Override
    public User findById(int id) {
        System.out.println("User find by ID " + id);
        return null;
    }

    @Override
    public List<User> findByName(String name) {
        return List.of();
    }

    @Override
    public List<User> findByRole(String role) {
        return List.of();
    }

    @Override
    public List<User> findByEmail(String email) {
        return List.of();
    }

    @Override
    public List<User> findByAddress(String address) {
        return List.of();
    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public void save(User user) {

    }
}
