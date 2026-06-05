package jdbcExam02;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@Slf4j
@SpringBootTest(classes = JdbcExam02.class)
class UserDaoImplTest {

    @Autowired
    private UserDao userDao;

    @BeforeEach
    void setUp() {
        log.info("@ 초기화 할 사항은 여기에 구현");
    }

    @AfterEach
    void tearDown() {
        log.info("@ 테스트 종료 후 정리할 것들 여기에 구현");
    }

//    @Test
//    void findAll() {
//        log.info("findAll 실행");
//    }
//
//    @Test
//    void findByName() {
//        log.info("findByName 실행");
//
//    }
//
//    @Test
//    void findByEmail() {
//        log.info("findByEmail 실행");
//
//    }
//
//    @Test
//    void findById() {
//        log.info("findById 실행");
//
//    }

    @Test
    void delete() {
        log.info("delete 실행");
        List<User> userList = userDao.findByName("test");
        for (User user : userList) {
            if (user.getName().equals("test")) {
                userDao.delete(user.getId());
            }
        }
    }

    @Test
    @DisplayName("Save Test")
    void save() {
        log.info("save 실행");
        User user = new User();
        user.setName("test");
        user.setEmail("test@eamil.com");
        user.setPassword("123456");
        userDao.save(user);
    }

    @Test
    @DisplayName("Update user test")
    void update() {
        log.info("update 실행");
//        List<User> userList = userDao.findByName("test");
//        for (User user : userList) {
//            if (user.getName().equals("test")) {
//                user.setPassword("123456");
//                userDao.update(user);
//            }
//        }
        User user = userDao.findById(1L);
        user.setName("test");
        userDao.update(user);
    }
}