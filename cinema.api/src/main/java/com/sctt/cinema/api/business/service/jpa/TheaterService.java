package com.sctt.cinema.api.business.service.jpa;

import com.sctt.cinema.api.business.entity.jpa.Theater;
import com.sctt.cinema.api.business.repository.TheaterRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
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
    @PostConstruct
    protected void init() {
        if (cacheMap == null) {
            cacheMap = new HashMap<>();

            repo.findAll().forEach(c -> cacheMap.put(c.theaterID,c));
        }
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

        return (t == null ? findByIdInRepository(key) : t);
    }

    @Override
    protected Theater findByIdInRepository(String key) {
        Theater t = null;

        boolean hasInRepo = repo.findById(key).isPresent();
        if (hasInRepo){
            t = repo.findById(key).get();
            cacheMap.put(key,t);
        }

        return t;
    }

    @Override
    public void delete(String key) {
        cacheMap.remove(key);
        repo.deleteById(key);
    }
}
