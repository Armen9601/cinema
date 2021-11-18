package com.example.web.controller;

import com.example.web.security.CurrentUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BlogController {

    @GetMapping("/blog")
    public String home(@AuthenticationPrincipal CurrentUser currentUser,
                       ModelMap modelMap) {
        modelMap.addAttribute("user", currentUser);
        return "blog";
    }

}
