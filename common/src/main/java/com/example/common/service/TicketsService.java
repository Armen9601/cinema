package com.example.common.service;

import com.example.common.entity.Tickets;

import java.time.LocalDateTime;
import java.util.List;

public interface TicketsService {

   void addTicket (Tickets tickets);

   List<Tickets> findBySeanceTime(LocalDateTime seanceTime);
}
