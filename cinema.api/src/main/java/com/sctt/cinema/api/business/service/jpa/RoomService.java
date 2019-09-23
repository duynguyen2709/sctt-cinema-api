package com.sctt.cinema.api.business.service.jpa;

import com.sctt.cinema.api.business.entity.jpa.Room;
import com.sctt.cinema.api.business.entity.jpa.Theater;
import com.sctt.cinema.api.business.repository.RoomRepository;
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

@Service
public class RoomService extends BaseJPAService<Room,Integer>{

    @Autowired
    private RoomRepository repo;

    @Override
    @EventListener(ApplicationReadyEvent.class)
    protected void init() {
        loadCacheMap(CacheKeyEnum.ROOM);
    }

    @Override
    public List<Room> findAll() {
        List<Room> list = new ArrayList<>(cacheMap.values());

        return list;
    }

    @Override
    public Room create(Room room) {
        Room t = repo.save(room);

        cacheMap.put(t.roomID,t);

        return t;
    }

    @Override
    public Room update(Room room) {
        Room t = repo.save(room);

        cacheMap.replace(t.roomID,t);

        return t;
    }

    @Override
    public Room findById(Integer key) {
        Room t = cacheMap.get(key);

        return t;
    }

    @Override
    public void delete(Integer key) {
        repo.deleteById(key);
        cacheMap.remove(key);
    }
}
