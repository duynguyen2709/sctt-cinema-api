package com.sctt.cinema.api.business.service;

import com.sctt.cinema.api.business.entity.jpa.Showtime;
import com.sctt.cinema.api.business.service.jpa.ShowtimeService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class AutoInsertShowtimeService {

    @Autowired
    private ShowtimeService showtimeService;

    @Order(Ordered.LOWEST_PRECEDENCE)
    @EventListener(ApplicationReadyEvent.class)
    public void autoInsert() {
//        int i = 0;
//        try {
//            for (int theaterID = 1; theaterID <= 77; theaterID++) {
//                int movieID = 3;
//                for (int format = 1; format <= 2; format++) {
//                    for (int timeFrom = 0; timeFrom < 3; timeFrom++) {
//                        int roomID  = (theaterID - 1) * 4 + format + 2;
//                        Showtime s = new Showtime();
//                        s.theaterID = theaterID;
//                        s.roomID = roomID;
//                        s.movieID = movieID;
//                        s.movieFormat = format;
//                        s.setTimeFrom(1571820000000L + 86400 * 1000 * timeFrom);
//                        showtimeService.create(s);
//                        log.info("Insert {}", i);
//                        i++;
//                    }
//                }
//            }
//        } catch (Exception e) {
//            log.error("AutoInsertShowtimeService {}", e.getMessage());
//        }
//        log.info("AutoInsertShowtimeService done {} showtimes", i);
    }
}