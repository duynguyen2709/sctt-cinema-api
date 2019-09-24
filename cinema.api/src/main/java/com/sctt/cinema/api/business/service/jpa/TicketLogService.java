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
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TicketLogService extends BaseJPAService<TicketLog,Long>{

    @Autowired
    private TicketLogRepository repo;

    public static long CURRENT_TICKET_ID = 0L;

    @Override
    @EventListener(ApplicationReadyEvent.class)
    @Order(2)
    protected void init() {
        loadCacheMap(CacheKeyEnum.TICKET_LOG);

        CURRENT_TICKET_ID = cacheMap.keySet().size() > 0 ? Collections.max(cacheMap.keySet()) : 0;
    }

    @Override
    public List<TicketLog> findAll() {
        List<TicketLog> list = new ArrayList<>(cacheMap.values());

        return list;
    }

    @Override
    public TicketLog create(TicketLog entity) {
        entity.setOrderTime(System.currentTimeMillis());
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
    public TicketLog findById(Long key) {
        TicketLog t = cacheMap.get(key);

        return t;
    }

    @Override
    public void delete(Long key) {
        repo.deleteById(key);
        cacheMap.remove(key);
    }
}
