package com.example.web.util;

import com.example.common.entity.User;
import com.example.common.enums.UserType;

import com.example.common.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class OnApplicationStartEvent implements ApplicationListener {

    private final UserRepository userRepository;

   private final PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(final ApplicationEvent event) {
        if (!userRepository.findByEmail("admin@mail.com").isPresent()) {

            userRepository.save(User.builder()
                    .name("admin")
                    .surname("admin")
                    .phoneNumber("111111")
                    .email("admin@mail.com")
                    .password(passwordEncoder.encode("admin"))
                    .userType(UserType.ADMIN)
                    .build());
        }
    }

}
