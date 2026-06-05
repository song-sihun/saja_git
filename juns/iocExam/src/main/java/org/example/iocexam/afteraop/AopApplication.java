package org.example.iocexam.afteraop;
import org.example.iocexam.afteraop.service.SimpleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AopApplication {

    public static void main(String[] args) {
        SpringApplication.run(AopApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(UserDao userDao, AopService aopService,  SimpleService simpleService) {
        return args -> {
            userDao.addUser();
            aopService.testService();
            simpleService.simpleMethod();
        };
    }
}