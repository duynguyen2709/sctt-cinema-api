package com.sctt.cinema.api.business.controller.client;

import com.sctt.cinema.api.business.entity.jpa.Movie;
import com.sctt.cinema.api.business.entity.jpa.Showtime;
import com.sctt.cinema.api.business.entity.jpa.Theater;
import com.sctt.cinema.api.business.entity.response.MovieListDTO;
import com.sctt.cinema.api.business.entity.response.RoomStatusDTO;
import com.sctt.cinema.api.business.entity.response.SeatStatusDTO;
import com.sctt.cinema.api.business.entity.response.ShowtimeDTO;
import com.sctt.cinema.api.business.service.jpa.*;
import com.sctt.cinema.api.common.BaseResponse;
import com.sctt.cinema.api.common.enums.MovieFormatEnum;
import com.sctt.cinema.api.common.enums.MovieStatusEnum;
import com.sctt.cinema.api.common.enums.ReturnCodeEnum;
import com.sctt.cinema.api.common.enums.ShowtimeTypeEnum;
import com.sctt.cinema.api.util.DateTimeUtils;
import com.sctt.cinema.api.util.TimeComparator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/client/public")
@Log4j2
public class ClientPublicController {

    //<editor-fold defaultstate="collapsed" desc="Services">
    @Autowired
    private MovieService movieService;

    @Autowired
    private TheaterService theaterService;

    @Autowired
    private ShowtimeService showtimeService;

    @Autowired
    private BookedSeatService bookedSeatService;

    @Autowired
    private RoomSeatService roomSeatService;
    //</editor-fold>

    @GetMapping("/movies")
    public BaseResponse homeScreen() {
        BaseResponse res = new BaseResponse(ReturnCodeEnum.SUCCESS);

        try {
            MovieListDTO data = new MovieListDTO();
            data.currentShowingMovies = movieService.findByStatus(MovieStatusEnum.CURRENT_SHOWING);
            data.comingSoonMovies = movieService.findByStatus(MovieStatusEnum.COMING_SOON);

            res.data = data;
        } catch (Exception e) {
            log.error("[homeScreen] ex: {}", e.getMessage());
            res = BaseResponse.EXCEPTION_RESPONSE;
        }

        return res;
    }

    @GetMapping("/showtimes/status/{showtimeID}")
    public BaseResponse getRoomStatus(@PathVariable("showtimeID") int showtimeID) {
        BaseResponse res = new BaseResponse(ReturnCodeEnum.SUCCESS);

        try {
            Showtime showtime = showtimeService.findById(showtimeID);
            if (showtime == null) {
                return new BaseResponse(ReturnCodeEnum.SHOWTIME_NOT_FOUND);
            }

            RoomStatusDTO data = new RoomStatusDTO();
            data.showtimeID = showtimeID;
            data.roomID = showtime.roomID;
            data.seats = convertSeatStatus(showtime);

            res.data = data;
        } catch (Exception e) {
            log.error("[getRoomStatus] ex: {}", e.getMessage());
            res = BaseResponse.EXCEPTION_RESPONSE;
        }

        return res;
    }

    @GetMapping("/theaters")
    public BaseResponse getProvinceTheater() {
        BaseResponse res = new BaseResponse(ReturnCodeEnum.SUCCESS);

        try {
            res.data = theaterService.getProvinceTheaterMap();
        } catch (Exception e) {
            log.error("[getProvinceTheater] ex: {}", e.getMessage());
            res = BaseResponse.EXCEPTION_RESPONSE;
        }

        return res;
    }

    private SeatStatusDTO[][] convertSeatStatus(Showtime showtime) {
        int showtimeID = showtime.showtimeID;
        int roomID     = showtime.roomID;

        SeatStatusDTO[][] res = null;

        try {
            res = roomSeatService.convert(roomSeatService.findById(roomID).seats);

            for (SeatStatusDTO[] arr : res) {
                for (SeatStatusDTO seatStatusDTO : arr) {
                    if (seatStatusDTO == null) {
                        continue;
                    }

                    String key = String.format("%s_%s_%s", roomID, seatStatusDTO.seatCode, showtimeID);
                    if (bookedSeatService.findById(key) != null) {
                        seatStatusDTO.status = 1;
                    }
                }
            }
        } catch (Exception e) {
            log.error("[convertSeatStatus] ex: {}", e.getMessage());
            throw e;
        }

        return res;
    }

    @GetMapping("/showtimes")
    public BaseResponse getShowtimeDetail(@RequestParam(required = false) Integer type,
                                          @RequestParam(required = false) Integer id,
                                          @RequestParam(required = false) String date) {
        BaseResponse res = new BaseResponse(ReturnCodeEnum.SUCCESS);

        try {
            ReturnCodeEnum checkParam = validateParam(type, id, date);
            if (checkParam != ReturnCodeEnum.SUCCESS) {
                return new BaseResponse(checkParam);
            }

            ShowtimeDTO dto = new ShowtimeDTO();

            List<Showtime> listShowtimeFiltered = showtimeService.findAll()
                            .stream()
                            .filter(c -> c.status == 1 && date.equals(DateTimeUtils.parseTimestampToString(c.getTimeFrom(), "yyyyMMdd")))
                            .collect(Collectors.toList());

            if (type == ShowtimeTypeEnum.MOVIE.getValue()) {
                listShowtimeFiltered = listShowtimeFiltered.stream()
                        .filter(c -> c.movieID == id)
                        .collect(Collectors.toList());

                for (Showtime showtime : listShowtimeFiltered) {
                    String theaterName = theaterService.findById(showtime.theaterID).theaterName;

                    if (!dto.showtimes.containsKey(theaterName)) {
                        dto.showtimes.put(theaterName, new HashMap<>());
                    }

                    Map<String, List<ShowtimeDTO.ShowtimeDetailDTO>> detailMap = dto.showtimes.get(theaterName);

                    String format = MovieFormatEnum.fromInt(showtime.movieFormat).getName();
                    if (!detailMap.containsKey(format)) {
                        detailMap.put(format, new ArrayList<>());
                    }

                    detailMap.get(format).add(new ShowtimeDTO.ShowtimeDetailDTO(showtime));
                }

            } else if (type == ShowtimeTypeEnum.THEATER.getValue()) {
                listShowtimeFiltered = listShowtimeFiltered.stream()
                                .filter(c -> c.theaterID == id)
                                .collect(Collectors.toList());

                for (Showtime showtime : listShowtimeFiltered) {
                    String movieName = movieService.findById(showtime.movieID).movieName;

                    if (!dto.showtimes.containsKey(movieName)) {
                        dto.showtimes.put(movieName, new HashMap<>());
                    }

                    Map<String, List<ShowtimeDTO.ShowtimeDetailDTO>> detailMap = dto.showtimes.get(movieName);

                    String format = MovieFormatEnum.fromInt(showtime.movieFormat).getName();
                    if (!detailMap.containsKey(format)) {
                        detailMap.put(format, new ArrayList<>());
                    }

                    detailMap.get(format).add(new ShowtimeDTO.ShowtimeDetailDTO(showtime));
                }
            }

            for (Map<String, List<ShowtimeDTO.ShowtimeDetailDTO>> map : dto.showtimes.values()){
                for (List<ShowtimeDTO.ShowtimeDetailDTO> list : map.values()){
                    list.sort(new TimeComparator());
                }
            }

            res.data = dto;
        } catch (Exception e) {
            log.error("[getShowtimeDetail] type {}, id {}, date {}, ex: {}", type, id, date, e.getMessage());
            res = BaseResponse.EXCEPTION_RESPONSE;
        }

        return res;
    }

    private ReturnCodeEnum validateParam(Integer type, Integer id, String date) {

        if (type == null || (type != ShowtimeTypeEnum.MOVIE.getValue()
                && type != ShowtimeTypeEnum.THEATER.getValue())) {
            return ReturnCodeEnum.PARAM_TYPE_INVALID;
        }

        if (id == null) {
            return ReturnCodeEnum.PARAM_ID_INVALID;
        }

        if (date == null || date.isEmpty() || DateTimeUtils.parseStringToDate(date, "yyyyMMdd") == null) {
            return ReturnCodeEnum.PARAM_DATE_INVALID;
        }

        if (type == ShowtimeTypeEnum.MOVIE.getValue()) {
            Movie movie = movieService.findById(id);
            if (movie == null || movie.status == MovieStatusEnum.STOP_SHOWING.getValue()) {
                return ReturnCodeEnum.MOVIE_NOT_FOUND;
            }
        } else {
            Theater t = theaterService.findById(id);
            if (t == null) {
                return ReturnCodeEnum.THEATER_NOT_FOUND;
            }
        }

        return ReturnCodeEnum.SUCCESS;
    }
}