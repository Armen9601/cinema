package com.example.web;

import com.example.common.properties.FoodProperties;
import com.example.common.properties.MovieProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"com.example.common.*", "com.example.web.*"})
@EnableJpaRepositories(basePackages = {"com.example.common.*", "com.example.web.*"})
@EntityScan("com.example.common.*")
@EnableConfigurationProperties({
        FoodProperties.class,
        MovieProperties.class
})
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }


}
