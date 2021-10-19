package com.example.common.service.serviceImpl;

import com.example.common.entity.Tickets;
import com.example.common.repository.TicketsRepository;
import com.example.common.service.TicketsService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class TicketsServicelmpl implements TicketsService {

    private final TicketsRepository ticketsRepository;


    @Override
    public void addTicket(Tickets tickets) {
        ticketsRepository.save(tickets);
    }

    @Override
    public List<Tickets> findBySeanceTime(LocalDateTime seanceTime) {
        return ticketsRepository.findBySeanceTime(seanceTime);
    }
}
