package com.sctt.cinema.api.business.service;

import com.sctt.cinema.api.business.entity.jpa.Movie;
import com.sctt.cinema.api.business.service.jpa.MovieService;
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
public class UpdateMovieStatusService {

    @Autowired
    private MovieService movieService;

    @Scheduled(cron = "0 0 0 * * ?")
    public void autoUpdateMovieStatus(){
        List<Movie> listMovie = movieService.findAll()
                .stream()
                .filter(c -> c.status == MovieStatusEnum.COMING_SOON.getValue())
                .collect(Collectors.toList());

        listMovie.forEach(c -> {
            if (DateTimeUtils.compareDateToNow(c.dateFrom) == MovieStatusEnum.CURRENT_SHOWING.getValue())
                c.status = MovieStatusEnum.CURRENT_SHOWING.getValue();
                movieService.update(c);
                log.info("autoUpdateMovieStatus: {}", GsonUtils.toJsonString(c));
        });
    }
}