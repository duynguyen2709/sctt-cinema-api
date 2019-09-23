package com.sctt.cinema.api.business.service.jpa;

import com.sctt.cinema.api.business.entity.jpa.BookedSeat;
import com.sctt.cinema.api.business.entity.jpa.Seat;
import com.sctt.cinema.api.business.repository.BookedSeatRepository;
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
public class BookedSeatService extends BaseJPAService<BookedSeat, String>{

    @Autowired
    private BookedSeatRepository repo;

    @Override
    @EventListener(ApplicationReadyEvent.class)
    protected void init() {
        loadCacheMap(CacheKeyEnum.BOOKED_SEAT);
    }

    @Override
    public List<BookedSeat> findAll() {
        List<BookedSeat> list = new ArrayList<>(cacheMap.values());

        return list;
    }

    @Override
    public BookedSeat create(BookedSeat entity) {
        BookedSeat t = repo.save(entity);

        cacheMap.put(t.getKey(),t);

        return t;
    }

    @Override
    public BookedSeat update(BookedSeat entity) {
        BookedSeat t = repo.save(entity);

        cacheMap.replace(t.getKey(),t);

        return t;
    }

    @Override
    public BookedSeat findById(String key) {
        BookedSeat t = cacheMap.get(key);

        return t;
    }

    @Override
    public void delete(String key) {
        repo.deleteById(new BookedSeat.BookedSeatKey(key));
        cacheMap.remove(key);
    }
}
