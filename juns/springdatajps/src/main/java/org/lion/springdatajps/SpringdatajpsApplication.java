package org.lion.springdatajps;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Optional;

@Slf4j
@SpringBootApplication
public class SpringdatajpsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringdatajpsApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(UserRepository repository) {
        return args -> {
//          User user = new User("admin", "admin@email.com");
//          repository.save(user);

          Optional<User> findUser = repository.findById(1L);
          log.info(findUser.toString());



        };
    }

}
