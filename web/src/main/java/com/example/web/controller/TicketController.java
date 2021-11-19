package com.example.web.controller;

import com.example.common.service.FoodService;
import com.example.common.service.MovieService;
import com.example.common.service.TicketsService;
import com.example.common.dto.BasketDto;
import com.example.web.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller
@RequiredArgsConstructor
public class TicketController {

    private final MovieService movieService;
    private final TicketsService ticketsService;
    private final FoodService foodService;


    @GetMapping("/bookTicket")
    public String seatPlan(
            @RequestParam("movieId") int movieId,
            @RequestParam(value = "seance", required = false) Integer seance,
            ModelMap modelMap
    ) {
        modelMap.addAttribute("movie", movieService.getById(movieId));
        modelMap.addAttribute("seance", seance);
        return "movie-seat-plan";
    }

    @GetMapping("/user/proceed")
    public String checkout(ModelMap modelMap,
                           HttpSession httpSession) {
        BasketDto basketDto = ((BasketDto) httpSession.getAttribute("basket"));
        if (basketDto == null) {
            return "redirect:/user/viewAll";
        }
        modelMap.addAttribute("basketDto", basketDto);
        modelMap.addAttribute("total", foodService.totalPrice(httpSession));
        return "movie-checkout";
    }

     @PostMapping("/buy-ticket")
    public String buyTicket(HttpSession session,
                            @RequestParam(value = "myPlace") List<String> myPlaces,
                            @RequestParam(value = "seance") String seance,
                            @RequestParam(value = "movieId") int movieId) {
        session.setAttribute("basket", new BasketDto(myPlaces, new ArrayList<>(), seance, movieId));
        return "redirect:/popcorn";
    }


    @PostMapping("/user/makePayment")
    public String makePayment(HttpSession session, @AuthenticationPrincipal CurrentUser currentUser, Locale locale) {
        int id = ((BasketDto) session.getAttribute("basket")).getMovieId();
        ticketsService.addTicket(session, currentUser.getUser(), locale);
        return "redirect:/bookTicket?movieId=" + id;
    }

}

