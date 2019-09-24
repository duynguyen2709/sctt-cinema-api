package com.sctt.cinema.api.business.service.jpa;

import com.sctt.cinema.api.business.entity.jpa.Movie;
import com.sctt.cinema.api.business.repository.MovieRepository;
import com.sctt.cinema.api.common.enums.CacheKeyEnum;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService extends BaseJPAService<Movie,Integer>{

    @Autowired
    private MovieRepository repo;

    @Override
    @EventListener(ApplicationReadyEvent.class)
    @Order(2)
    protected void init() {
        loadCacheMap(CacheKeyEnum.MOVIE);
    }

    @Override
    public List<Movie> findAll() {
        List<Movie> list = new ArrayList<>(cacheMap.values());

        return list;
    }

    @Override
    public Movie create(Movie entity) {
        Movie t = repo.save(entity);

        cacheMap.put(t.movieID,t);

        return t;
    }

    @Override
    public Movie update(Movie entity) {
        Movie t = repo.save(entity);

        cacheMap.replace(t.movieID,t);

        return t;
    }

    @Override
    public Movie findById(Integer key) {
        Movie t = cacheMap.get(key);

        return t;
    }

    @Override
    public void delete(Integer key) {
        repo.deleteById(key);
        cacheMap.remove(key);
    }
}
