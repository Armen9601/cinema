package com.example.web.security;

import com.example.common.entity.User;
import org.springframework.security.core.authority.AuthorityUtils;

public class CurrentUser extends org.springframework.security.core.userdetails.User {

    private final User user;

    public CurrentUser(User user) {
        super(
                user.getEmail(),
                user.getPassword(),
                AuthorityUtils.createAuthorityList(user.getUserType().name())
        );
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
