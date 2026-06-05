package jdbcExam02;

import java.util.List;

public interface UserDao {
    public List<User> findAll();
    public List<User> findByName(String name);
    public List<User> findByEmail(String email);
    public User findById(Long id);
    public int delete(Long id);
    public int save(User user);
    public int update(User user);
}
