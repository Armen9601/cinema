package com.example.common.repository;

import com.example.common.entity.Tickets;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketsRepository extends JpaRepository<Tickets,Integer> {


}
