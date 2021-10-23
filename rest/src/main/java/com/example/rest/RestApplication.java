package com.example.rest;

import com.example.common.properties.MovieProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"com.example.common.*", "com.example.rest.*"})
@EnableJpaRepositories(basePackages = {"com.example.common.*", "com.example.rest.*"})
@EntityScan("com.example.common.*")

public class RestApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestApplication.class, args);

    }

}
