package com.example.web.controller;

import com.example.common.entity.User;
import com.example.common.service.EmailService;
import com.example.common.service.UserService;
import com.example.web.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final EmailService emailService;

    @GetMapping("/register")
    public String register() {
        return "sign-up";
    }

    @GetMapping("/user/myLikedMovie")
    public String myLikedMovie(ModelMap modelMap, @AuthenticationPrincipal CurrentUser currentUser) {
        modelMap.addAttribute("movies",currentUser.getUser().getMyLikedMovie());
        return "my-liked-movie";
    }

    @GetMapping("/sendMessage")
    public String sendMessage(@RequestParam String name,@RequestParam String message) {
        emailService.sendSimpleMessage("2021JavaForTest@gmail.com", name, message);
        return "redirect:/contact";
    }

    @PostMapping("/sign-up")
    public String AddUser(@ModelAttribute @Valid User user, BindingResult result) {
        if (result.hasErrors() || !userService.add(user)) {
            return "sign-up";
        }
        userService.add(user);
        return "redirect:/";
    }
}
