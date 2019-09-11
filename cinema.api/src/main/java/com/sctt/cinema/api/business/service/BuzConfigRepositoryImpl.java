package com.sctt.cinema.api.business.service;

import com.sctt.cinema.api.business.entity.jpa.BuzConfig;
import com.sctt.cinema.api.business.repository.BaseCRUDRepository;
import com.sctt.cinema.api.business.repository.BuzConfigRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class BuzConfigRepositoryImpl implements BaseCRUDRepository<BuzConfig, BuzConfig.BuzConfigKey> {

    @Autowired
    private BuzConfigRepository repo;

    @Override
    public List<BuzConfig> findAll() {
        return repo.findAll();
    }

    @Override
    public BuzConfig create(BuzConfig buzConfig) {
        return repo.save(buzConfig);
    }

    @Override
    public BuzConfig update(BuzConfig buzConfig) {
        return repo.save(buzConfig);
    }

    @Override
    public BuzConfig find(BuzConfig.BuzConfigKey key) {
        return repo.findById(key).orElse(null);
    }

    @Override
    public void delete(BuzConfig.BuzConfigKey key) {
        repo.deleteById(key);
    }
}
