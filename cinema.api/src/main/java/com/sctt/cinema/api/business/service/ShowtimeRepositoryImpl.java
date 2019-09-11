package com.sctt.cinema.api.business.service;

import com.sctt.cinema.api.business.entity.jpa.Showtime;
import com.sctt.cinema.api.business.repository.BaseCRUDRepository;
import com.sctt.cinema.api.business.repository.ShowtimeRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class ShowtimeRepositoryImpl implements BaseCRUDRepository<Showtime,Integer> {

    @Autowired
    private ShowtimeRepository repo;

    @Override
    public List<Showtime> findAll() {
        return repo.findAll();
    }

    @Override
    public Showtime create(Showtime showtime) {
        return repo.save(showtime);
    }

    @Override
    public Showtime update(Showtime showtime) {
        return repo.save(showtime);
    }

    @Override
    public Showtime find(Integer key) {
        return repo.findById(key).orElse(null);
    }

    @Override
    public void delete(Integer key) {
        repo.deleteById(key);
    }
}
