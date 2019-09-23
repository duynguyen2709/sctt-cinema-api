package com.sctt.cinema.api.business.service;

import com.hazelcast.transaction.TransactionContext;
import com.sctt.cinema.api.business.entity.CacheMaps;
import com.sctt.cinema.api.business.entity.jpa.Movie;
import com.sctt.cinema.api.business.entity.jpa.Theater;
import com.sctt.cinema.api.business.entity.jpa.User;
import com.sctt.cinema.api.business.repository.MovieRepository;
import com.sctt.cinema.api.business.repository.TheaterRepository;
import com.sctt.cinema.api.business.repository.UserRepository;
import com.sctt.cinema.api.util.HazelCastUtils;
import com.sctt.cinema.api.common.enums.CacheKeyEnum;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Log4j2
public class CacheService {

    @Value("${hazelcast.useHazelCast}")
    private boolean useHazelcast;

    @Autowired
    private TheaterRepository theaterRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;

    private static HazelCastUtils hazelCast = HazelCastUtils.getInstance();

    private <K, V> Map<K, V> reload(Map<K, V> mapFromDb, CacheKeyEnum hazelCastKey){

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
            log.error(String.format("[CacheService] load [%s] ex: %s", hazelCastKey.name(), ex.getMessage()));

            return mapFromDb;
        }

        return hazelCast.getMap(hazelCastKey.name());
    }

    private Map loadMovieMap(){
        if (CacheMaps.MOVIE_MAP != null) {
            CacheMaps.MOVIE_MAP.clear();
            CacheMaps.MOVIE_MAP = null;
        }

        Map<Integer, Movie> map = new HashMap<>();

        movieRepository.findAll().forEach(c -> map.put(c.movieID,c));

        CacheMaps.MOVIE_MAP = reload(map, CacheKeyEnum.MOVIE);
        log.info("MOVIE_MAP loaded succeed");

        return CacheMaps.MOVIE_MAP;
    }

    private Map loadTheaterMap(){
        if (CacheMaps.THEATER_MAP != null) {
            CacheMaps.THEATER_MAP.clear();
            CacheMaps.THEATER_MAP = null;
        }

        Map<Integer, Theater> map = new HashMap<>();

        theaterRepository.findAll().forEach(c -> map.put(c.theaterID,c));

        CacheMaps.THEATER_MAP = reload(map, CacheKeyEnum.THEATER);
        log.info("THEATER_MAP loaded succeed");

        return CacheMaps.THEATER_MAP;
    }

    public Map loadCacheMap(CacheKeyEnum type) {
        switch (type){
            case THEATER:
                return loadTheaterMap();

            case MOVIE:
                return loadMovieMap();

            case USER:
                return loadUserMap();

            case ALL:
            default:
                 return null;
        }
    }

    private Map loadUserMap() {
        if (CacheMaps.USER_MAP != null) {
            CacheMaps.USER_MAP.clear();
            CacheMaps.USER_MAP = null;
        }

        Map<String, User> map = new HashMap<>();

        userRepository.findAll().forEach(c -> map.put(c.email,c));

        CacheMaps.USER_MAP = reload(map, CacheKeyEnum.USER);
        log.info("USER_MAP loaded succeed");

        return CacheMaps.USER_MAP;
    }
}