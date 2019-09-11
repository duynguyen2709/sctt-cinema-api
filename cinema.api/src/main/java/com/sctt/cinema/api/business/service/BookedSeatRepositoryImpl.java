package com.sctt.cinema.api.business.service;

import com.sctt.cinema.api.business.entity.jpa.BookedSeat;
import com.sctt.cinema.api.business.repository.BaseCRUDRepository;
import com.sctt.cinema.api.business.repository.BookedSeatRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class BookedSeatRepositoryImpl implements BaseCRUDRepository<BookedSeat, BookedSeat.BookedSeatKey> {

    @Autowired
    private BookedSeatRepository repo;

    @Override
    public List<BookedSeat> findAll() {
        return repo.findAll();
    }

    @Override
    public BookedSeat create(BookedSeat bookedSeat) {
        return repo.save(bookedSeat);
    }

    @Override
    public BookedSeat update(BookedSeat bookedSeat) {
        return repo.save(bookedSeat);
    }

    @Override
    public BookedSeat find(BookedSeat.BookedSeatKey key) {
        return repo.findById(key).orElse(null);
    }

    @Override
    public void delete(BookedSeat.BookedSeatKey key) {
        repo.deleteById(key);
    }
}
