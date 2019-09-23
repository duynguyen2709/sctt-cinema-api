package com.sctt.cinema.api.business.controller.rest;

import com.sctt.cinema.api.business.entity.jpa.Showtime;
import com.sctt.cinema.api.business.service.jpa.ShowtimeService;
import com.sctt.cinema.api.business.service.jpa.ShowtimeService;
import com.sctt.cinema.api.common.BaseResponse;
import com.sctt.cinema.api.common.enums.ReturnCodeEnum;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Log4j2
public class ShowtimeRestController {

    @Autowired
    private ShowtimeService service;

    @GetMapping("/showtimes")
    public BaseResponse findAll(){
        BaseResponse res = new BaseResponse(ReturnCodeEnum.SUCCESS);

        try{
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
            res.data = service.create(entity);
        } catch (Exception e){
            log.error("[insert] ex: {}",e.getMessage());
            res = BaseResponse.EXCEPTION_RESPONSE;
        }

        return res;
    }

    @PutMapping("/showtimes/{showtimeID}")
    public BaseResponse insert(@PathVariable Integer showtimeID,@RequestBody Showtime entity){
        BaseResponse res = new BaseResponse(ReturnCodeEnum.SUCCESS);

        try{
            entity.showtimeID = showtimeID;
            res.data = service.update(entity);
        } catch (Exception e){
            log.error("[insert] ex: {}",e.getMessage());
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