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
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService extends BaseJPAService<User,String>{

    @Autowired
    private UserRepository repo;

    @Override
    @EventListener(ApplicationReadyEvent.class)
    @Order(2)
    protected void init() {
        loadCacheMap(CacheKeyEnum.USER);
    }

    @Override
    public List<User> findAll() {
        List<User> list = new ArrayList<>(cacheMap.values());

        return list;
    }

    @Override
    public User create(User user) {
        user.creationDate = new Timestamp(System.currentTimeMillis());
        user.totalAccumulation = 0;
        user.password = new BCryptPasswordEncoder().encode(user.password);
        User t = repo.save(user);

        cacheMap.put(t.email, t);

        return t;
    }

    @Override
    public User update(User user) {
        User old = findById(user.email);
        user.password = old.password;
        user.creationDate = old.creationDate;

        User t = repo.save(user);

        cacheMap.replace(t.email, t);

        return t;
    }

    @Override
    public User findById(String key) {
        User t = cacheMap.get(key);

        return t;
    }

    @Override
    public void delete(String key) {
        repo.deleteById(key);
        cacheMap.remove(key);
    }

    public User updatePassword(User user){
        User old = findById(user.email);
        old.password = new BCryptPasswordEncoder().encode(user.password);

        User t = repo.save(old);

        cacheMap.replace(t.email, t);

        return t;
    }
}