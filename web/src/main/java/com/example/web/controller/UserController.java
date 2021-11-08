package com.example.web.controller;

import com.example.common.entity.Movie;
import com.example.common.entity.User;
import com.example.common.enums.UserType;
import com.example.common.repository.MovieRepository;
import com.example.common.service.MovieService;
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
    private final MovieService movieService;

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

    @GetMapping("/myLikedMovie")
    public String myLikedMovie(ModelMap modelMap, @AuthenticationPrincipal CurrentUser currentUser) {
        modelMap.addAttribute("movie",currentUser.getUser().getMyLikedMovie());
        return "myLikedMovie";
    }

    @GetMapping("/user/likeMovie")
    public String likeMovie(@RequestParam("id") int movieId, @AuthenticationPrincipal CurrentUser currentUser) {
        userService.update(currentUser.getUser(),movieId);
        return "redirect:/user/viewAll";
    }

}
