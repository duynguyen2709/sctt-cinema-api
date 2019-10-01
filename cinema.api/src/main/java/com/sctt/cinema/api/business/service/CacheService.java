package com.sctt.cinema.api.business.service;

import com.hazelcast.transaction.TransactionContext;
import com.sctt.cinema.api.business.entity.CacheMaps;
import com.sctt.cinema.api.business.entity.jpa.*;
import com.sctt.cinema.api.business.entity.request.RoomSeatMappingDTO;
import com.sctt.cinema.api.business.repository.*;
import com.sctt.cinema.api.util.GsonUtils;
import com.sctt.cinema.api.util.HazelCastUtils;
import com.sctt.cinema.api.common.enums.CacheKeyEnum;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Log4j2
public class CacheService {

    @Value("${hazelcast.useHazelCast}")
    private boolean useHazelcast;

    //<editor-fold defaultstate="collapsed" desc="Repositories">
    @Autowired
    private TheaterRepository theaterRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private BuzConfigRepository buzConfigRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private ShowtimeRepository showtimeRepository;

    @Autowired
    private TicketLogRepository ticketLogRepository;

    @Autowired
    private BookedSeatRepository bookedSeatRepository;

    //</editor-fold>

    private static HazelCastUtils hazelCast = HazelCastUtils.getInstance();

    public Map loadCacheMap(CacheKeyEnum type) {

        switch (type){
            case THEATER:
                return loadTheaterMap();

            case MOVIE:
                return loadMovieMap();

            case USER:
                return loadUserMap();

            case ROOM:
                return loadRoomMap();

            case SEAT:
                return loadSeatMap();

            case SHOWTIME:
                return loadShowtimeMap();

            case TICKET_LOG:
                return loadTicketLogMap();

            case BUZ_CONFIG:
                return loadBuzMap();

            case BOOKED_SEAT:
                return loadBookedSeatMap();

            case ROOM_SEAT:
                return loadRoomSeatMap();

            case ALL:
            default:
                 return null;
        }
    }

    private <K, V> Map<K, V> reload(Map<K, V> mapFromDb, CacheKeyEnum hazelCastKey){

        if (!useHazelcast) {
            return mapFromDb;
        }

        TransactionContext context = hazelCast.getTransactionContext();

        try {
            context.beginTransaction();
            hazelCast.reloadMap(context, hazelCastKey.name(), mapFromDb);
            context.commitTransaction();
        } catch (Exception ex) {
            context.rollbackTransaction();
            log.error(String.format("[CacheService] load [%s] ex: %s", hazelCastKey.name(), ex.getMessage()));

            return mapFromDb;
        }

        return hazelCast.getMap(hazelCastKey.name());
    }

    private Map loadUserMap() {
        if (CacheMaps.USER_MAP != null) {
            CacheMaps.USER_MAP.clear();
            CacheMaps.USER_MAP = null;
        }

        Map<String, User> map = new HashMap<>();

        userRepository.findAll().forEach(c -> map.put(c.email,c));

        CacheMaps.USER_MAP = reload(map, CacheKeyEnum.USER);
        log.info("USER_MAP loaded succeed");

        return CacheMaps.USER_MAP;
    }

    private Map loadSeatMap() {
        if (CacheMaps.SEAT_MAP != null) {
            CacheMaps.SEAT_MAP.clear();
            CacheMaps.SEAT_MAP = null;
        }

        Map<String, Seat> map = new HashMap<>();

        seatRepository.findAll().forEach(c -> map.put(c.getKey(),c));

        CacheMaps.SEAT_MAP = reload(map, CacheKeyEnum.SEAT);
        log.info("SEAT_MAP loaded succeed");

        return CacheMaps.SEAT_MAP;
    }

    private Map loadBookedSeatMap() {
        if (CacheMaps.BOOKED_SEAT_MAP != null) {
            CacheMaps.BOOKED_SEAT_MAP.clear();
            CacheMaps.BOOKED_SEAT_MAP = null;
        }

        Map<String, BookedSeat> map = new HashMap<>();

        bookedSeatRepository.findAll().forEach(c -> map.put(c.getKey(),c));

        CacheMaps.BOOKED_SEAT_MAP = reload(map, CacheKeyEnum.BOOKED_SEAT);
        log.info("BOOKED_SEAT_MAP loaded succeed");

        return CacheMaps.BOOKED_SEAT_MAP;
    }

    private Map loadBuzMap() {
        if (CacheMaps.BUZ_CONFIG_MAP != null) {
            CacheMaps.BUZ_CONFIG_MAP.clear();
            CacheMaps.BUZ_CONFIG_MAP = null;
        }

        Map<String, BuzConfig> map = new HashMap<>();

        buzConfigRepository.findAll().forEach(c -> map.put(c.getKey(),c));

        CacheMaps.BUZ_CONFIG_MAP = reload(map, CacheKeyEnum.BUZ_CONFIG);
        log.info("BUZ_CONFIG_MAP loaded succeed");

        return CacheMaps.BUZ_CONFIG_MAP;
    }

    private Map loadRoomMap() {
        if (CacheMaps.ROOM_MAP != null) {
            CacheMaps.ROOM_MAP.clear();
            CacheMaps.ROOM_MAP = null;
        }

        Map<Integer, Room> map = new HashMap<>();

        roomRepository.findAll().forEach(c -> map.put(c.roomID,c));

        CacheMaps.ROOM_MAP = reload(map, CacheKeyEnum.ROOM);
        log.info("ROOM_MAP loaded succeed");

        return CacheMaps.ROOM_MAP;
    }

    private Map loadTicketLogMap() {
        if (CacheMaps.TICKET_LOG_MAP != null) {
            CacheMaps.TICKET_LOG_MAP.clear();
            CacheMaps.TICKET_LOG_MAP = null;
        }

        Map<Long, TicketLog> map = new HashMap<>();

        ticketLogRepository.findAll().forEach(c -> map.put(c.ticketID,c));

        CacheMaps.TICKET_LOG_MAP = reload(map, CacheKeyEnum.TICKET_LOG);
        log.info("TICKET_LOG_MAP loaded succeed");

        return CacheMaps.TICKET_LOG_MAP;
    }

    private Map loadShowtimeMap() {
        if (CacheMaps.SHOWTIME_MAP != null) {
            CacheMaps.SHOWTIME_MAP.clear();
            CacheMaps.SHOWTIME_MAP = null;
        }

        Map<Integer, Showtime> map = new HashMap<>();

        showtimeRepository.findAll().forEach(c -> map.put(c.showtimeID,c));

        CacheMaps.SHOWTIME_MAP = reload(map, CacheKeyEnum.SHOWTIME);
        log.info("SHOWTIME_MAP loaded succeed");

        return CacheMaps.SHOWTIME_MAP;
    }

    private Map loadMovieMap(){
        if (CacheMaps.MOVIE_MAP != null) {
            CacheMaps.MOVIE_MAP.clear();
            CacheMaps.MOVIE_MAP = null;
        }

        Map<Integer, Movie> map = new HashMap<>();

        movieRepository.findAll().forEach(c -> map.put(c.movieID,c));

        CacheMaps.MOVIE_MAP = reload(map, CacheKeyEnum.MOVIE);
        log.info("MOVIE_MAP loaded succeed");

        return CacheMaps.MOVIE_MAP;
    }

    private Map loadTheaterMap(){
        if (CacheMaps.THEATER_MAP != null) {
            CacheMaps.THEATER_MAP.clear();
            CacheMaps.THEATER_MAP = null;
        }

        Map<Integer, Theater> map = new HashMap<>();

        theaterRepository.findAll().forEach(c -> map.put(c.theaterID,c));

        CacheMaps.THEATER_MAP = reload(map, CacheKeyEnum.THEATER);
        log.info("THEATER_MAP loaded succeed");

        return CacheMaps.THEATER_MAP;
    }

    private Map loadRoomSeatMap() {
        if (CacheMaps.ROOM_SEAT_MAP != null){
            CacheMaps.ROOM_SEAT_MAP.clear();
            CacheMaps.ROOM_SEAT_MAP = null;
        }

        Map<Integer, RoomSeatMappingDTO> map = new HashMap<>();

        //load
        List<Seat> seats = seatRepository.findAll();

        for (Seat s : seats) {
            if (!map.containsKey(s.roomID))
                map.put(s.roomID, new RoomSeatMappingDTO(s.roomID));

            RoomSeatMappingDTO dto = map.get(s.roomID);
            if (dto.numOfRow < s.rowNo)
                dto.numOfRow = s.rowNo;

            if (dto.numOfColumn < s.columnNo)
                dto.numOfColumn = s.columnNo;
        }

        for (Seat s : seats) {
            RoomSeatMappingDTO dto = map.get(s.roomID);
            if (dto.seats == null)
                dto.seats = new Seat[dto.numOfRow + 1][dto.numOfColumn + 1];

            dto.seats[s.rowNo][s.columnNo] = s;
        }

        CacheMaps.ROOM_SEAT_MAP = reload(map,CacheKeyEnum.ROOM_SEAT);
        log.info("ROOM_SEAT_MAP loaded succeed");

        return CacheMaps.ROOM_SEAT_MAP;
    }
}
