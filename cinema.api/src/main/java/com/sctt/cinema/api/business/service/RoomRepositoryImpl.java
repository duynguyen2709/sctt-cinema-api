package com.sctt.cinema.api.business.service;

import com.sctt.cinema.api.business.entity.jpa.Room;
import com.sctt.cinema.api.business.entity.jpa.Theater;
import com.sctt.cinema.api.business.repository.BaseCRUDRepository;
import com.sctt.cinema.api.business.repository.RoomRepository;
import com.sctt.cinema.api.business.repository.TheaterRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class RoomRepositoryImpl implements BaseCRUDRepository<Room,Integer> {

    @Autowired
    private RoomRepository repo;

    @Override
    public List<Room> findAll() {
        return repo.findAll();
    }

    @Override
    public Room create(Room room) {
        return repo.save(room);
    }

    @Override
    public Room update(Room room) {
        return repo.save(room);
    }

    @Override
    public Room find(Integer key) {
        return repo.findById(key).orElse(null);
    }

    @Override
    public void delete(Integer key) {

        repo.deleteById(key);
    }
}