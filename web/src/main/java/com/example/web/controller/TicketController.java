package com.example.web.controller;

import com.example.common.service.MovieService;
import com.example.common.service.TicketsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
    public String seatPlan(@RequestParam(value = "place",required = false) String place1, ModelMap modelMap) {
       String place;
        if (place1!=null){
            place=place1;
            modelMap.addAttribute("place",place);
        }else{
            place="nothing";
            modelMap.addAttribute("place",place);
        }

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
