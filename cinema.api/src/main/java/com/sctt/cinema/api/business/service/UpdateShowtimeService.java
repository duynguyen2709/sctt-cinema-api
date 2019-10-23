package com.sctt.cinema.api.business.service;

import com.sctt.cinema.api.business.entity.jpa.Movie;
import com.sctt.cinema.api.business.entity.jpa.Showtime;
import com.sctt.cinema.api.business.service.jpa.MovieService;
import com.sctt.cinema.api.business.service.jpa.ShowtimeService;
import com.sctt.cinema.api.common.enums.MovieStatusEnum;
import com.sctt.cinema.api.util.DateTimeUtils;
import com.sctt.cinema.api.util.GsonUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class UpdateShowtimeService {

    @Autowired
    private ShowtimeService showtimeService;

    @Scheduled(cron = "${schedule.movieStatusUpdate.cron}")
    public void autoUpdateShowtime(){

        log.info("### AutoUpdateShowtime triggered ###");

        List<Showtime> listShowtime = showtimeService.findAll();

        listShowtime.forEach(c -> {
            long timeFrom = c.getTimeFrom() + 86400 * 1000;
            long timeTo = c.getTimeTo() + 86400 * 1000;
            c.setTimeFrom(timeFrom);
            c.setTimeTo(timeTo);
            showtimeService.update(c);
        });

        log.info("### Updated {} entities ###", listShowtime.size());
        log.info("### End AutoUpdateShowtime ###");
    }
}