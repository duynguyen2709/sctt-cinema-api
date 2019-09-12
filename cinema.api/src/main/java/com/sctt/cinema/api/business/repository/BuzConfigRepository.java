package com.sctt.cinema.api.business.repository;

import com.sctt.cinema.api.business.entity.jpa.BuzConfig;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuzConfigRepository extends JpaRepository<BuzConfig, BuzConfig.BuzConfigKey> {
}
