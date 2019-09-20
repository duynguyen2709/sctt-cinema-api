package com.sctt.cinema.api.business.service.jpa;

import com.sctt.cinema.api.business.entity.jpa.Movie;
import com.sctt.cinema.api.business.entity.jpa.User;
import com.sctt.cinema.api.business.repository.MovieRepository;
import com.sctt.cinema.api.business.repository.UserRepository;
import com.sctt.cinema.api.common.enums.CacheKeyEnum;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
public class UserService extends BaseJPAService<User,String>{

    @Autowired
    private UserRepository repo;

    @Override
    @EventListener(ApplicationReadyEvent.class)
    protected void init() {
        loadCacheMap(CacheKeyEnum.USER);
    }

    @Override
    public List<User> findAll() {
        List<User> list = new ArrayList<>(cacheMap.values());

        return list;
    }

    @Override
    public User create(User theater) {
        User t = repo.save(theater);

        cacheMap.put(t.email,t);

        return t;
    }

    @Override
    public User update(User theater) {
        User t = repo.save(theater);

        cacheMap.replace(t.email,t);

        return t;
    }

    @Override
    public User findById(String key) {
        User t = cacheMap.get(key);

        return t;
    }

    @Override
    public void delete(String key) {
        cacheMap.remove(key);
        repo.deleteById(key);
    }
}
