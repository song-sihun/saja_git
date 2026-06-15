package org.lion.springsecurity.beforesecirity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.servlet.context.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class BeforeApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeforeApplication.class, args);
    }
}
