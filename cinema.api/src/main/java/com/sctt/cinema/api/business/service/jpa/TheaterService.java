package com.sctt.cinema.api.business.service.jpa;

import com.sctt.cinema.api.business.entity.CacheMaps;
import com.sctt.cinema.api.business.entity.jpa.Theater;
import com.sctt.cinema.api.business.repository.TheaterRepository;
import com.sctt.cinema.api.business.service.HazelCastService;
import com.sctt.cinema.api.common.enums.HazelCastKeyEnum;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Log4j2
@Service
public class TheaterService extends BaseJPAService<Theater,String>{

    @Autowired
    private TheaterRepository repo;

    @Override
    @EventListener(ApplicationReadyEvent.class)
    protected void init() {
        loadCacheMap(HazelCastKeyEnum.THEATER);
    }

    @Override
    public List<Theater> findAll() {
        List<Theater> list = new ArrayList<>(cacheMap.values());

        return list;
    }

    @Override
    public Theater create(Theater theater) {
        Theater t = repo.save(theater);

        cacheMap.put(t.theaterID,t);

        return t;
    }

    @Override
    public Theater update(Theater theater) {
        Theater t = repo.save(theater);

        cacheMap.replace(t.theaterID,t);

        return t;
    }

    @Override
    public Theater findById(String key) {
        Theater t = cacheMap.get(key);

        return t;
    }

    @Override
    public void delete(String key) {
        cacheMap.remove(key);
        repo.deleteById(key);
    }
}
