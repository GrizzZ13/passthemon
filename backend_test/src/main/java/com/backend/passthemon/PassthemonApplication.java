package com.backend.passthemon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
//@EnableJpaRepositories(basePackages = {"com.backend.passthemon.dao.jpa"})
//@EnableMongoRepositories(basePackages = {"com.backend.passthemon.dao.mongo"})
@EnableCaching
@EnableScheduling
public class PassthemonApplication {

    public static void main(String[] args) {
        SpringApplication.run(PassthemonApplication.class, args);
    }

}
