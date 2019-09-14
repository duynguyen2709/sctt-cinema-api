package com.sctt.cinema.api.business.repository;

import com.sctt.cinema.api.business.entity.jpa.Theater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface TheaterRepository extends JpaRepository<Theater,String> {
}
