package com.sctt.cinema.api.business.controller.client;

import com.sctt.cinema.api.business.entity.DTO.MovieListDTO;
import com.sctt.cinema.api.business.entity.DTO.RoomStatusDTO;
import com.sctt.cinema.api.business.entity.DTO.SeatStatusDTO;
import com.sctt.cinema.api.business.entity.jpa.Movie;
import com.sctt.cinema.api.business.service.jpa.MovieService;
import com.sctt.cinema.api.common.BaseResponse;
import com.sctt.cinema.api.common.enums.MovieStatusEnum;
import com.sctt.cinema.api.common.enums.ReturnCodeEnum;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/client/public")
@Log4j2
public class ClientPublicController {

    @Autowired
    private MovieService movieService;

    private static final List<List<SeatStatusDTO>> mockSeatStatus = Arrays.asList(
            Arrays.asList(new SeatStatusDTO("A1",1,0),
                    new SeatStatusDTO("A2",1,0),
                    new SeatStatusDTO("A3",1,0),
                    new SeatStatusDTO("A4",1,0),
                    new SeatStatusDTO("A5",1,0),
                    new SeatStatusDTO("A6",1,0),
                    new SeatStatusDTO("A7",1,0),
                    new SeatStatusDTO("A8",1,0)),
            Arrays.asList(new SeatStatusDTO("B1",1,0),
                    new SeatStatusDTO("B2",1,0),
                    new SeatStatusDTO("B3",1,0),
                    new SeatStatusDTO("B4",1,0),
                    new SeatStatusDTO("B5",1,0),
                    new SeatStatusDTO("B6",1,0),
                    new SeatStatusDTO("B7",1,0),
                    new SeatStatusDTO("B8",1,0)),
            Arrays.asList(new SeatStatusDTO("C1",1,0),
                    new SeatStatusDTO("C2",1,0),
                    new SeatStatusDTO("C3",1,1),
                    new SeatStatusDTO("C4",1,1),
                    new SeatStatusDTO("C5",1,1),
                    new SeatStatusDTO("C6",1,1),
                    new SeatStatusDTO("C7",1,1),
                    new SeatStatusDTO("C8",1,0)),
            Arrays.asList(new SeatStatusDTO("D1",1,0),
                    new SeatStatusDTO("D2",1,0),
                    new SeatStatusDTO("D3",1,1),
                    new SeatStatusDTO("D4",1,0),
                    new SeatStatusDTO("D5",1,0),
                    new SeatStatusDTO("D6",1,1),
                    new SeatStatusDTO("D7",1,0),
                    new SeatStatusDTO("D8",1,0)),
            Arrays.asList(new SeatStatusDTO("E1",1,0),
                    new SeatStatusDTO("E2",1,0),
                    new SeatStatusDTO("E3",1,0),
                    new SeatStatusDTO("E4",1,0),
                    new SeatStatusDTO("E5",1,1),
                    new SeatStatusDTO("E6",1,0),
                    new SeatStatusDTO("E7",1,0),
                    new SeatStatusDTO("E8",1,0)),
            Arrays.asList(new SeatStatusDTO("F1",1,0),
                    new SeatStatusDTO("F2",1,1),
                    new SeatStatusDTO("F3",1,1),
                    new SeatStatusDTO("F4",1,0),
                    new SeatStatusDTO("F5",1,0),
                    new SeatStatusDTO("F6",1,1),
                    new SeatStatusDTO("F7",1,0),
                    new SeatStatusDTO("F8",1,1)),
            Arrays.asList(new SeatStatusDTO("G1",2,0),
                    new SeatStatusDTO("G2",2,0),
                    new SeatStatusDTO("G3",2,0),
                    new SeatStatusDTO("G4",2,1),
                    new SeatStatusDTO("G5",2,1),
                    new SeatStatusDTO("G6",2,0),
                    new SeatStatusDTO("G7",2,0),
                    new SeatStatusDTO("G8",2,0))
    );

    @GetMapping("/movies")
    public BaseResponse homeScreen(){
        BaseResponse res = new BaseResponse(ReturnCodeEnum.SUCCESS);

        try{
            MovieListDTO data = new MovieListDTO();
            data.currentShowingMovies = getListMovies(MovieStatusEnum.CURRENT_SHOWING);
            data.comingSoonMovies = getListMovies(MovieStatusEnum.COMING_SOON);

            res.data = data;
        } catch (Exception e){
            log.error("[homeScreen] ex: {}",e.getMessage());
            res = BaseResponse.EXCEPTION_RESPONSE;
        }

        return res;
    }


    @GetMapping("/showtimes/{showtimeID}")
    public BaseResponse getRoomStatus(@PathVariable("showtimeID") int showtimeID){
        BaseResponse res = new BaseResponse(ReturnCodeEnum.SUCCESS);

        try{
            RoomStatusDTO data = new RoomStatusDTO();
            data.showtimeID = showtimeID;
            data.roomID = 1;
            data.seats = mockSeatStatus;

            res.data = data;
        } catch (Exception e){
            log.error("[getRoomStatus] ex: {}",e.getMessage());
            res = BaseResponse.EXCEPTION_RESPONSE;
        }

        return res;
    }
//
//    private List<List<SeatStatusDTO>> getListSeats(int roomID) {
//
//
//        return res;
//    }

    private List<Movie> getListMovies(MovieStatusEnum status) {
        List<Movie> result = movieService.findAll()
                .stream()
                .filter(c -> c.status == status.getValue())
                .collect(Collectors.toList());

        return result;
    }
}