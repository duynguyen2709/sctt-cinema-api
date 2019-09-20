package com.sctt.cinema.api.business.service.jpa;

import com.sctt.cinema.api.business.entity.jpa.Theater;
import com.sctt.cinema.api.business.repository.TheaterRepository;
import com.sctt.cinema.api.common.enums.CacheKeyEnum;
import com.sctt.cinema.api.common.enums.ProvinceEnum;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Service
public class TheaterService extends BaseJPAService<Theater,Integer>{

    @Autowired
    private TheaterRepository repo;

    @Override
    @EventListener(ApplicationReadyEvent.class)
    protected void init() {
        loadCacheMap(CacheKeyEnum.THEATER);
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
    public Theater findById(Integer key) {
        Theater t = cacheMap.get(key);

        return t;
    }

    @Override
    public void delete(Integer key) {
        cacheMap.remove(key);
        repo.deleteById(key);
    }

    public Map<String,List<Theater>> getProvinceTheaterMap(){
        Map<String,List<Theater>> res = new HashMap<>();

        cacheMap.values().forEach(c -> {
            String provinceName = ProvinceEnum.fromInt(c.provinceCode).toString();

            if (!res.containsKey(provinceName))
                res.put(provinceName,new ArrayList<>());

            res.get(provinceName).add(c);
        });

        return res;
    }
}
