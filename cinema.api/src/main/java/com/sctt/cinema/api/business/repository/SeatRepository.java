package com.sctt.cinema.api.business.repository;

import com.sctt.cinema.api.business.entity.jpa.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat,Integer> {

    List<Seat> findByRoomID(int roomID);
}
