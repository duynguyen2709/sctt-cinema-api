package com.sctt.cinema.api.business.entity;

import com.sctt.cinema.api.business.entity.jpa.*;

import java.util.Map;

public class CacheMaps {

    public static Map<Integer, Theater>   THEATER_MAP    = null;
    public static Map<Integer, Movie>     MOVIE_MAP      = null;
    public static Map<String, User>       USER_MAP       = null;
    public static Map<Integer, BuzConfig> BUZ_CONFIG_MAP = null;
    public static Map<Integer, Room>      ROOM_MAP       = null;
    public static Map<String, Seat>       SEAT_MAP       = null;
    public static Map<Integer, Showtime>  SHOWTIME_MAP   = null;
    public static Map<String, TicketLog>  TICKET_LOG_MAP = null;
    public static Map<String, BookedSeat>  BOOKED_SEAT_MAP = null;
}
