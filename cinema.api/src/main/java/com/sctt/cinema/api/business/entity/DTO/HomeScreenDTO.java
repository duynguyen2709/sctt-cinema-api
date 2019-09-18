package com.sctt.cinema.api.business.entity.DTO;

import com.sctt.cinema.api.business.entity.jpa.Movie;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HomeScreenDTO implements Serializable {
    public List<Movie>        currentShowingMovies;
    public List<Movie>        comingSoonMovies;
    public List<PromotionDTO> promotions;
}
