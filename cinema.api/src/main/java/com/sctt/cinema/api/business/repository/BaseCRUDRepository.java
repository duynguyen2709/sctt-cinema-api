package com.sctt.cinema.api.business.repository;

import java.util.List;

public interface BaseCRUDRepository<T,K> {
    List<T> findAll();
    T create(T t);
    T update(T t);
    T find(K key);
    void delete(K key);
}
