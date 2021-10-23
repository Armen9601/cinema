package com.example.web.security;


import com.example.common.entity.User;
import com.example.common.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SecurityService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public CurrentUser loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> byEmail = userRepository.findByEmail(email);
        if (byEmail.isEmpty()) {
            throw new UsernameNotFoundException(String.format("User with  %s username does not exists", email));
        }
        return new CurrentUser(byEmail.get());
    }
}