package com.example.common.service;

import com.example.common.entity.User;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

public interface TicketsService {

    void addTicket(HttpSession httpSession, User user, Locale locale);

    List<String> findBySeanceTime(LocalDateTime seanceTime);

    boolean existsByPlace(String place);

}
