package com.sctt.cinema.api.business.service.jpa;

import com.sctt.cinema.api.business.entity.jpa.BuzConfig;
import com.sctt.cinema.api.business.entity.jpa.Room;
import com.sctt.cinema.api.business.repository.BuzConfigRepository;
import com.sctt.cinema.api.business.repository.RoomRepository;
import com.sctt.cinema.api.common.enums.CacheKeyEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BuzConfigService extends BaseJPAService<BuzConfig, String>{

    @Autowired
    private BuzConfigRepository repo;

    @Override
    @EventListener(ApplicationReadyEvent.class)
    @Order(2)
    protected void init() {
        loadCacheMap(CacheKeyEnum.BUZ_CONFIG);
    }

    @Override
    public List<BuzConfig> findAll() {
        List<BuzConfig> list = new ArrayList<>(cacheMap.values());

        return list;
    }

    @Override
    public BuzConfig create(BuzConfig entity) {
        BuzConfig t = repo.save(entity);

        cacheMap.put(t.getKey(),t);

        return t;
    }

    @Override
    public BuzConfig update(BuzConfig entity) {
        BuzConfig t = repo.save(entity);

        cacheMap.replace(t.getKey(),t);

        return t;
    }

    @Override
    public BuzConfig findById(String key) {
        BuzConfig t = cacheMap.get(key);

        return t;
    }

    @Override
    public void delete(String key) {
        repo.deleteById(new BuzConfig.BuzConfigKey(key));
        cacheMap.remove(key);
    }
}
