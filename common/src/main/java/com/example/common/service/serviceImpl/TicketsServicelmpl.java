package com.example.common.service.serviceImpl;

import com.example.common.entity.Movie;
import com.example.common.entity.Tickets;
import com.example.common.entity.User;
import com.example.common.repository.TicketsRepository;
import com.example.common.service.EmailService;
import com.example.common.service.MovieService;
import com.example.common.service.TicketsService;
import com.example.common.dto.BasketDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


@Service
@RequiredArgsConstructor
public class TicketsServicelmpl implements TicketsService {

    private final TicketsRepository ticketsRepository;
    private final MovieService movieService;
    private final EmailService emailService;

    @SneakyThrows
    @Override
    public void addTicket(HttpSession httpSession, User user, Locale locale) {
        BasketDto basketDto = (BasketDto) httpSession.getAttribute("basket");
        String seanceTime = basketDto.getSeance().substring(16, 18);
        Movie byId = movieService.getById(basketDto.getMovieId());
        LocalDateTime seance = null;
        for (LocalDateTime localDateTime : byId.getSeanceDateTime()) {
            if (localDateTime.getHour() == Integer.parseInt(seanceTime)) {
                seance = localDateTime;
            }
        }
        for (String myPlace : basketDto.getMyPlaces()) {
            Tickets tickets = Tickets.builder()
                    .seanceTime(seance)
                    .movie(byId)
                    .place(myPlace)
                    .isSold(true)
                    .price(12)
                    .user(user).build();
            ticketsRepository.save(tickets);
        }
        emailService.sendHtmlEmail(user.getEmail(),"BuyTickets",user,"/","BuyTickets",locale);
        httpSession.removeAttribute("basket");
    }

    @Override
    public List<String> findBySeanceTime(LocalDateTime seanceTime) {
        List<String> places=new ArrayList<>();
        List<Tickets> bySeanceTime = ticketsRepository.findBySeanceTime(seanceTime);
        for (Tickets tickets : bySeanceTime) {
            places.add(tickets.getPlace());
        }
        return places;
    }

    @Override
    public boolean existsByPlace(String place) {
        return ticketsRepository.existsByPlace(place);
    }

    @Override
    public List<Tickets> getAllTickets() {
        return ticketsRepository.findAll();
    }

    @Override
    public Tickets save(Tickets tickets) {
        return ticketsRepository.save(tickets);
    }

}
