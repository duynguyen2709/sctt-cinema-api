package com.sctt.cinema.api.business.repository;

import com.sctt.cinema.api.business.entity.jpa.Movie;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MovieRepository extends JpaRepository<Movie,Integer> {
}
