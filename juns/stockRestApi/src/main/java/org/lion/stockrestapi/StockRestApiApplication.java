package org.lion.stockrestapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class StockRestApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(StockRestApiApplication.class, args);
    }

}
