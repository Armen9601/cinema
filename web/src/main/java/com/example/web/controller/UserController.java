package com.example.web.controller;

import com.example.common.entity.User;
import com.example.common.enums.UserType;
import com.example.common.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/register")
    public String register() {
        return "sign-up";
    }

    @PostMapping("/sign-up")
    public String AddUser(@ModelAttribute @Valid User user, BindingResult result) {
        if (result.hasErrors() || !userService.add(user)) {
            return "sign-up";
        }
        user.setUserType(UserType.USER);
        userService.add(user);
        return "redirect:/";
    }

}
