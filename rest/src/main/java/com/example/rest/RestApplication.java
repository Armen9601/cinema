package com.example.rest;

import com.example.common.entity.User;
import com.example.common.enums.UserType;
import com.example.common.properties.FoodProperties;
import com.example.common.properties.MovieProperties;
import com.example.common.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@ComponentScan({"com.example.common.*", "com.example.rest.*"})
@EnableJpaRepositories(basePackages = {"com.example.common.*", "com.example.rest.*"})
@EntityScan("com.example.common.*")
@EnableConfigurationProperties({
        FoodProperties.class,
        MovieProperties.class
})
public class RestApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestApplication.class, args);

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CommandLineRunner commandLineRunner(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByEmail("admin@mail.com").isEmpty()) {
                userRepository.save(User.builder()
                        .name("admin")
                        .surname("admin")
                        .phoneNumber("111111")
                        .email("admin@mail.com")
                        .password(passwordEncoder.encode("admin"))
                        .userType(UserType.ADMIN)
                        .build());
            }
        };
    }

}
