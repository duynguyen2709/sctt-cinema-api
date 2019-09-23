package com.sctt.cinema.api.business.service.jpa;

import com.sctt.cinema.api.business.entity.jpa.Movie;
import com.sctt.cinema.api.business.entity.jpa.TicketLog;
import com.sctt.cinema.api.business.repository.MovieRepository;
import com.sctt.cinema.api.business.repository.TicketLogRepository;
import com.sctt.cinema.api.common.enums.CacheKeyEnum;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TicketLogService extends BaseJPAService<TicketLog,String>{

    @Autowired
    private TicketLogRepository repo;

    @Override
    @EventListener(ApplicationReadyEvent.class)
    protected void init() {
        loadCacheMap(CacheKeyEnum.TICKET_LOG);
    }

    @Override
    public List<TicketLog> findAll() {
        List<TicketLog> list = new ArrayList<>(cacheMap.values());

        return list;
    }

    @Override
    public TicketLog create(TicketLog entity) {
        TicketLog t = repo.save(entity);

        cacheMap.put(t.ticketID,t);

        return t;
    }

    @Override
    public TicketLog update(TicketLog entity) {
        TicketLog t = repo.save(entity);

        cacheMap.replace(t.ticketID,t);

        return t;
    }

    @Override
    public TicketLog findById(String key) {
        TicketLog t = cacheMap.get(key);

        return t;
    }

    @Override
    public void delete(String key) {
        repo.deleteById(key);
        cacheMap.remove(key);
    }
}
