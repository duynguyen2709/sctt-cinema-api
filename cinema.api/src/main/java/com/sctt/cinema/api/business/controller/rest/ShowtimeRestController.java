package com.sctt.cinema.api.business.controller.rest;

import com.sctt.cinema.api.business.entity.jpa.Movie;
import com.sctt.cinema.api.business.entity.jpa.Showtime;
import com.sctt.cinema.api.business.service.jpa.MovieService;
import com.sctt.cinema.api.business.service.jpa.ShowtimeService;
import com.sctt.cinema.api.business.service.jpa.ShowtimeService;
import com.sctt.cinema.api.common.BaseResponse;
import com.sctt.cinema.api.common.enums.ReturnCodeEnum;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api")
@Log4j2
public class ShowtimeRestController {

    @Autowired
    private ShowtimeService service;

    @Autowired
    private MovieService movieService;

    @GetMapping("/showtimes")
    public BaseResponse findAll(){
        BaseResponse res = new BaseResponse(ReturnCodeEnum.SUCCESS);

        try{
            List<Showtime> data = service.findAll();
            if (data != null){
                data.sort(new Comparator<Showtime>() {
                    @Override public int compare(Showtime o1, Showtime o2) {
                        return Integer.compare(o1.showtimeID, o2.showtimeID);
                    }
                });
            }
            res.data = service.findAll();
        } catch (Exception e){
            log.error("[findAll] ex: {}",e.getMessage());
            res = BaseResponse.EXCEPTION_RESPONSE;
        }

        return res;
    }

    @GetMapping("/showtimes/{showtimeID}")
    public BaseResponse findByID(@PathVariable Integer showtimeID){
        BaseResponse res = new BaseResponse(ReturnCodeEnum.SUCCESS);

        try{
            res.data = service.findById(showtimeID);
            if (res.data == null)
                res = new BaseResponse(ReturnCodeEnum.SHOWTIME_NOT_FOUND);
        } catch (Exception e){
            log.error("[findByID] ex: {}",e.getMessage());
            res = BaseResponse.EXCEPTION_RESPONSE;
        }

        return res;
    }

    @PostMapping("/showtimes")
    public BaseResponse insert(@RequestBody Showtime entity){
        BaseResponse res = new BaseResponse(ReturnCodeEnum.SUCCESS);

        try{
            if (!entity.isValid()){
                return new BaseResponse(ReturnCodeEnum.DATA_NOT_VALID);
            }

            if (!checkValidTime(entity)){
                return new BaseResponse(ReturnCodeEnum.SHOWTIME_TIMEFROM_NOT_VALID);
            }

            res.data = service.create(entity);
        } catch (Exception e){
            log.error("[insert] ex: {}",e.getMessage());
            res = BaseResponse.EXCEPTION_RESPONSE;
        }

        return res;
    }

    private boolean checkValidTime(Showtime entity) {
        long  timeFrom = entity.timeFrom.getTime();
        Movie movie    = movieService.findById(entity.movieID);
        long  timeTo   = entity.timeFrom.getTime() + movie.timeInMinute * 60 * 1000;

        return service.findAll().stream().anyMatch(c -> c.status == 1 &&
                c.roomID == entity.roomID &&
                (c.timeTo.getTime() > timeFrom || c.timeFrom.getTime() < timeTo));
    }

    @PutMapping("/showtimes/{showtimeID}")
    public BaseResponse update(@PathVariable Integer showtimeID,@RequestBody Showtime entity){
        BaseResponse res = new BaseResponse(ReturnCodeEnum.SUCCESS);

        try{
            entity.showtimeID = showtimeID;
            if (!entity.isValid()){
                return new BaseResponse(ReturnCodeEnum.DATA_NOT_VALID);
            }

            if (!checkValidTime(entity)){
                return new BaseResponse(ReturnCodeEnum.SHOWTIME_TIMEFROM_NOT_VALID);
            }

            res.data = service.update(entity);
        } catch (Exception e){
            log.error("[update] ex: {}",e.getMessage());
            res = BaseResponse.EXCEPTION_RESPONSE;
        }

        return res;
    }

    @DeleteMapping("/showtimes/{showtimeID}")
    public BaseResponse delete(@PathVariable Integer showtimeID){
        BaseResponse res = new BaseResponse(ReturnCodeEnum.SUCCESS);

        try{
            service.delete(showtimeID);
            res.data = true;
        } catch (Exception e){
            log.error("[delete] ex: {}",e.getMessage());
            res = BaseResponse.EXCEPTION_RESPONSE;
        }

        return res;
    }
}
