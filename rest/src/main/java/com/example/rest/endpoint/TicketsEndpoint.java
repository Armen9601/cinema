package com.example.rest.endpoint;

import com.example.common.entity.Movie;
import com.example.common.entity.Tickets;
import com.example.common.service.MovieService;
import com.example.common.service.TicketsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TicketsEndpoint {
    private TicketsService ticketsService;
    private final MovieService movieService;

    @GetMapping("/book/Ticket")
    public List<Tickets> getAllTickets(){
        return ticketsService.getAllTickets();
    }
    @GetMapping("/bookTicket")
    public Movie seatPlan(@RequestParam int id){
        return movieService.getById(id);
    }
    @PostMapping("/user/makePayment")
    public Tickets saveTicket(@RequestBody Tickets tickets){
        return ticketsService.save(tickets);
    }

}
