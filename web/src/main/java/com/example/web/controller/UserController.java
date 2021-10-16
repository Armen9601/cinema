package com.example.web.controller;

import com.example.common.entity.User;
import com.example.common.enums.UserType;
import com.example.common.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor

public class UserController {

    private final UserService userService;

    @GetMapping("/register")
    public String register(){
        return "sign-up";
    }


    @PostMapping("/sign-up")
    public String AddUser(@ModelAttribute User user) {
        user.setUserType(UserType.USER);
            userService.addUser(user);

        return "redirect:/";
    }

}
