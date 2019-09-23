package com.sctt.cinema.api.business.service.jpa;

import com.sctt.cinema.api.business.service.CacheService;
import com.sctt.cinema.api.common.enums.CacheKeyEnum;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public abstract class BaseJPAService<T,K>  {

    protected Map<K,T> cacheMap = null;

    @Autowired
    private CacheService cacheService;

    protected void loadCacheMap(CacheKeyEnum type){
        if (cacheMap == null)
            cacheMap = cacheService.loadCacheMap(type);
    }

    protected abstract void init();
    public abstract  List<T> findAll();
    public abstract T create(T t);
    public abstract T update(T t);
    public abstract T findById(K key);
    public abstract void delete(K key);
}
