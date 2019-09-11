package com.sctt.cinema.api.business.repository;

import com.sctt.cinema.api.business.entity.jpa.BookedSeat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookedSeatRepository extends JpaRepository<BookedSeat, BookedSeat.BookedSeatKey> {
}
