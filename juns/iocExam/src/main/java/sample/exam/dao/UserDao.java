package sample.exam.dao;

import sample.exam.domain.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserDao {
    public User getUserByName(String username);
    public User getUserByEmail(String email);
    public List<User> getAllUsers();
    public void saveUser(User user);
}
