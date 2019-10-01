package com.sctt.cinema.api.business.service.jpa;

import com.sctt.cinema.api.business.entity.jpa.BuzConfig;
import com.sctt.cinema.api.business.entity.jpa.Movie;
import com.sctt.cinema.api.business.entity.jpa.Showtime;
import com.sctt.cinema.api.business.repository.BuzConfigRepository;
import com.sctt.cinema.api.business.repository.ShowtimeRepository;
import com.sctt.cinema.api.common.enums.CacheKeyEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShowtimeService extends BaseJPAService<Showtime,Integer>{

    @Autowired
    private ShowtimeRepository repo;

    @Autowired
    private MovieService movieService;

    @Override
    @EventListener(ApplicationReadyEvent.class)
    @Order(2)
    protected void init() {
        loadCacheMap(CacheKeyEnum.SHOWTIME);
    }

    @Override
    public List<Showtime> findAll() {
        List<Showtime> list = new ArrayList<>(cacheMap.values());

        return list;
    }

    @Override
    public Showtime create(Showtime entity) {
        Movie movie = movieService.findById(entity.movieID);
        entity.setTimeTo(entity.timeFrom.getTime() + movie.timeInMinute * 60 * 1000);
        entity.status = 1;

        Showtime t = repo.save(entity);

        cacheMap.put(t.showtimeID,t);

        return t;
    }

    @Override
    public Showtime update(Showtime entity) {
        Movie movie = movieService.findById(entity.movieID);
        entity.setTimeTo(entity.timeFrom.getTime() + movie.timeInMinute * 60 * 1000);

        Showtime t = repo.save(entity);

        cacheMap.replace(t.showtimeID,t);

        return t;
    }

    @Override
    public Showtime findById(Integer key) {
        Showtime t = cacheMap.get(key);

        return t;
    }

    @Override
    public void delete(Integer key) {
        repo.deleteById(key);
        cacheMap.remove(key);
    }
}
