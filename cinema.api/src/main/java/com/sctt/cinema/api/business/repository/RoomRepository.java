package com.sctt.cinema.api.business.repository;

import com.sctt.cinema.api.business.entity.jpa.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room,Integer> {
}
