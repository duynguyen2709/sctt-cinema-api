package com.sctt.cinema.api.business.repository;

import com.sctt.cinema.api.business.entity.jpa.Showtime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShowtimeRepository extends JpaRepository<Showtime,Integer> {
}