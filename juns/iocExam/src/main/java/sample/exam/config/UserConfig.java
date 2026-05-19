package sample.exam.config;

import sample.exam.dao.UserDao;
import sample.exam.dao.UserDaoImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sample.bean.Dice;

@Configuration
//@ComponentScan(basePackages = "org.com.example.ioxexam")
public class UserConfig {
    @Bean
    public Dice dice() {
        return new Dice(6);
    }

    @Bean
    public UserDao userDao() {
        return new UserDaoImpl();
    }
//
//    @Bean
//    public UserService userService(UserDao userDao) {
//        return new UserServiceImpl(userDao);
//    }
//
//    @Bean
//    public UserController userController(UserService userService) {
//        return new UserController(userService);
//    }
}
