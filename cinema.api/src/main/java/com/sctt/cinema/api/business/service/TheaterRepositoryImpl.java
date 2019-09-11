package com.sctt.cinema.api.business.service;

import com.sctt.cinema.api.business.entity.jpa.Theater;
import com.sctt.cinema.api.business.repository.BaseCRUDRepository;
import com.sctt.cinema.api.business.repository.TheaterRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class TheaterRepositoryImpl implements BaseCRUDRepository<Theater,String> {

    @Autowired
    private TheaterRepository repo;

    public List<Theater> findAll(){
        return repo.findAll();
    }

    @Override
    public Theater create(Theater theater) {
       return repo.save(theater);
    }

    @Override
    public Theater update(Theater theater) {
        return repo.save(theater);
    }

    @Override
    public Theater find(String key) {
        return repo.findById(key).orElse(null);
    }

    @Override
    public void delete(String key) {
        repo.deleteById(key);
    }

}