package com.example.web.controller;

import com.example.common.service.MovieService;
import com.example.common.service.TicketsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class TicketController {
    private final MovieService movieService;

    private final TicketsService ticketsService;

    @GetMapping("/bookTicket")
    public String seatPlan() {

        return "movie-seat-plan";
    }

    @PostMapping("seatPlan")
    public String addticket(@RequestParam LocalDateTime seanceTime, @RequestParam String place) {

        return "redirect: /index";
    }

    @GetMapping("checkout")
    public String checkout() {
        return "movie-checkout";
    }


}
