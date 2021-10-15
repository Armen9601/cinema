package com.example.web.controller;

import com.example.common.entity.Movie;
import com.example.common.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class TicketController {
    private final MovieService movieService;

    @GetMapping("/seat")
    public String movieTicketPlan() {
        return "events";
    }

}
