package com.sctt.cinema.api.business.service;

import com.sctt.cinema.api.business.entity.jpa.Movie;
import com.sctt.cinema.api.business.repository.BaseCRUDRepository;
import com.sctt.cinema.api.business.repository.MovieRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class MovieRepositoryImpl implements BaseCRUDRepository<Movie,Integer> {

    @Autowired
    private MovieRepository repo;

    @Override
    public List<Movie> findAll() {
        return repo.findAll();
    }

    @Override
    public Movie create(Movie movie) {
        return repo.save(movie);
    }

    @Override
    public Movie update(Movie movie) {
        return repo.save(movie);
    }

    @Override
    public Movie find(Integer key) {
        return repo.findById(key).orElse(null);
    }

    @Override
    public void delete(Integer key) {
        repo.deleteById(key);
    }
}
