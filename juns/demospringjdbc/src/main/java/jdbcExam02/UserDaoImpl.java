package jdbcExam02;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {

    private final JdbcTemplate jdbcTemplate;
    private final BeanPropertyRowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);
    @Override
    public List<User> findAll() {
        String sql = "select * from users";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public List<User> findByName(String name) {
        String sql = "select * from users where name = ?";
        return jdbcTemplate.query(sql, rowMapper, name);
    }

    @Override
    public List<User> findByEmail(String email) {
        String sql = "select * from users where email = ?";
        return jdbcTemplate.query(sql, rowMapper, email);

    }

    @Override
    public User findById(Long id) {
        String sql = "select * from users where id = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    @Override
    public int delete(Long id) {
        String sql = "delete from users where id = ?";
        return jdbcTemplate.update(sql, id);
    }

    @Override
    public int save(User user) {
        String sql = "insert into users(name, email) values (?, ?)";
        return jdbcTemplate.update(sql, user.getName(), user.getEmail());
    }

    @Override
    public int update(User user) {
        String sql = "update users set name = ?, email = ? where id = ?";
        return jdbcTemplate.update(sql, user.getName(), user.getEmail(), user.getId());
    }


}
