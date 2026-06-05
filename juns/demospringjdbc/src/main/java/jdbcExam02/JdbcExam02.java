package jdbcExam02;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@SpringBootApplication
public class JdbcExam02 implements CommandLineRunner {
    private final UserDao userDao;

    public static void main(String[] args) {
        SpringApplication.run(JdbcExam02.class, args);
    }

    @Override
    public void run(String... args) throws Exception {


//
//        User user = userDao.findById(1L);
//        log.info(user.toString());
//
//        user.setPassword("password");
//        int resultCount = userDao.update(user);
//        log.info("Result Count : {}", resultCount);
//
//        User user2 = new User();
//        user2.setId(2L);
//        user2.setName("name");
//        user2.setEmail("email");
//        user2.setPassword("password");
//
//        int saveResult = userDao.save(user2);
//        log.info("Save Result : {}", saveResult);
//
//        List<User> users1 = userDao.findAll();
//        for (User temp : users1) {
//            log.info(temp.toString());
//        }
//
//
//        int deleteCount = userDao.delete(user2.getId());
//        log.info("Delete Count : {}", deleteCount);
//
//
//        List<User> users2 = userDao.findAll();
//        for (User temp : users2) {
//            log.info(temp.toString());
//        }


    }
}
