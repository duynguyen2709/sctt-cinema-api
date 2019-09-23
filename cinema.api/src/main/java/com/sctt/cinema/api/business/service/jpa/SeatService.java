package com.sctt.cinema.api.business.service.jpa;

import com.sctt.cinema.api.business.entity.jpa.Movie;
import com.sctt.cinema.api.business.entity.jpa.Seat;
import com.sctt.cinema.api.business.repository.MovieRepository;
import com.sctt.cinema.api.business.repository.SeatRepository;
import com.sctt.cinema.api.common.enums.CacheKeyEnum;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SeatService extends BaseJPAService<Seat, String>{

    @Autowired
    private SeatRepository repo;

    @Override
    @EventListener(ApplicationReadyEvent.class)
    protected void init() {
        loadCacheMap(CacheKeyEnum.SEAT);
    }

    @Override
    public List<Seat> findAll() {
        List<Seat> list = new ArrayList<>(cacheMap.values());

        return list;
    }

    @Override
    public Seat create(Seat entity) {
        Seat t = repo.save(entity);

        cacheMap.put(t.getKey(),t);

        return t;
    }

    @Override
    public Seat update(Seat entity) {
        Seat t = repo.save(entity);

        cacheMap.replace(t.getKey(),t);

        return t;
    }

    @Override
    public Seat findById(String key) {
        Seat t = cacheMap.get(key);

        return t;
    }

    @Override
    public void delete(String key) {
        repo.deleteById(new Seat.SeatKey(key));
        cacheMap.remove(key);
    }
}
