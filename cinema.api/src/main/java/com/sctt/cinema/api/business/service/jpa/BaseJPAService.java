package com.sctt.cinema.api.business.service.jpa;

import com.sctt.cinema.api.business.service.HazelCastService;
import com.sctt.cinema.api.common.enums.HazelCastKeyEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Map;

public abstract class BaseJPAService<T,K>  {

    protected Map<K,T> cacheMap = null;

    @Autowired
    private HazelCastService hazelCastService;

    protected void loadCacheMap(HazelCastKeyEnum type){
        if (cacheMap == null)
            cacheMap = hazelCastService.loadCacheMap(type);
    }

    protected abstract void init();
    public abstract  List<T> findAll();
    public abstract T create(T t);
    public abstract T update(T t);
    public abstract T findById(K key);
    protected abstract T findByIdInRepository(K key);
    public abstract void delete(K key);
}
