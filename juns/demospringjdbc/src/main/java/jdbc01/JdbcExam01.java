package jdbc01;

import jdbcExam02.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@SpringBootApplication
public class JdbcExam01 implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(JdbcExam01.class, args);

    }

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
//        String sqlInsert = "insert into users(name, email) values (?,?)";
//        String sql = "select * from users where id = ?";
//
//        int resultCount = jdbcTemplate.update(sqlInsert, "jun", "jun@email.com");
//
//        log.info("result count : {}", resultCount);


        String sql2 = "select * from users";

        List<User> userList = jdbcTemplate.query(sql2, new BeanPropertyRowMapper<>(User.class));

        for (User user : userList) {
            log.info("user : {}", user);
        }

        RowMapper<User> rowMapper = new RowMapper<User>(){

            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                return user;
            }
        };

        List<User> userList2 = jdbcTemplate.query(sql2, rowMapper);
        for (User user : userList2) {
            log.info("user : {}", user);
        }


    }
}
