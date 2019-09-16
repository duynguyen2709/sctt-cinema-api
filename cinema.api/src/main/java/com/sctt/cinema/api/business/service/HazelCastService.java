package com.sctt.cinema.api.business.service;

import com.hazelcast.transaction.TransactionContext;
import com.sctt.cinema.api.business.entity.CacheMaps;
import com.sctt.cinema.api.business.entity.config.HazelCastConfig;
import com.sctt.cinema.api.business.entity.jpa.Theater;
import com.sctt.cinema.api.business.repository.TheaterRepository;
import com.sctt.cinema.api.util.HazelCastUtil;
import com.sctt.cinema.api.common.enums.HazelCastKeyEnum;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Log4j2
public class HazelCastService {

    @Value("${hazelcast.useHazelCast}")
    private boolean useHazelcast;

    @Autowired
    private TheaterRepository theaterRepository;

    private static HazelCastUtil hazelCast = HazelCastUtil.getInstance();

    public <K, V> Map<K, V> reload(Map<K, V> mapFromDb, HazelCastKeyEnum hazelCastKey){

        if (!useHazelcast) {
            return mapFromDb;
        }

        TransactionContext context = hazelCast.getTransactionContext();

        try {
            context.beginTransaction();
            hazelCast.reloadMap(context, hazelCastKey.name(), mapFromDb);
            context.commitTransaction();
        } catch (Exception ex) {
            context.rollbackTransaction();
            log.error(String.format("[HazelCastService] load [%s] ex: %s", hazelCastKey.name(), ex.getMessage()));

            return mapFromDb;
        }

        return hazelCast.getMap(hazelCastKey.name());
    }

    public Map loadTheaterMap(){
        if (CacheMaps.THEATER_MAP != null) {
            CacheMaps.THEATER_MAP.clear();
            CacheMaps.THEATER_MAP = null;
        }

        Map<String, Theater> map = new HashMap<>();

        theaterRepository.findAll().forEach(c -> map.put(c.theaterID,c));

        CacheMaps.THEATER_MAP = reload(map,HazelCastKeyEnum.THEATER);
        log.info("THEATER_MAP loaded succeed");

        return CacheMaps.THEATER_MAP;
    }

    public Map loadCacheMap(HazelCastKeyEnum type) {
        switch (type){
            case THEATER:
                return loadTheaterMap();

            case ALL:
            default:
                 return null;
        }
    }
}
