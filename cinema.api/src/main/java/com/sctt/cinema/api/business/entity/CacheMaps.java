package com.sctt.cinema.api.business.entity;

import com.sctt.cinema.api.business.entity.jpa.Movie;
import com.sctt.cinema.api.business.entity.jpa.Theater;
import com.sctt.cinema.api.business.entity.jpa.User;

import java.util.Map;

public class CacheMaps {

    public static Map<Integer, Theater> THEATER_MAP = null;
    public static Map<Integer, Movie>  MOVIE_MAP = null;
    public static Map<String, User> USER_MAP = null;
}
