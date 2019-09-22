package com.sctt.cinema.api.business.controller.rest;

import com.sctt.cinema.api.business.entity.jpa.Seat;
import com.sctt.cinema.api.business.repository.SeatRepository;
import com.sctt.cinema.api.common.BaseResponse;
import com.sctt.cinema.api.common.enums.ReturnCodeEnum;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api")
@Log4j2
public class SeatRestController {

    @Autowired
    private SeatRepository repo;

    @GetMapping("/seat")
    public BaseResponse findAll(){
        BaseResponse res = new BaseResponse(ReturnCodeEnum.SUCCESS);

        try{
            res.data = repo.findAll();
        } catch (Exception e){
            log.error("[findAll] ex: {}",e.getMessage());
            res = BaseResponse.EXCEPTION_RESPONSE;
        }

        return res;
    }

    @GetMapping("/seat/{seatID}")
    public BaseResponse findByID(@PathVariable Seat.SeatID seatID){
        BaseResponse res = new BaseResponse(ReturnCodeEnum.SUCCESS);

        try{
            res.data = repo.findById(seatID).get();
        } catch (Exception e){
            log.error("[findByID] ex: {}",e.getMessage());
            res = BaseResponse.EXCEPTION_RESPONSE;
        }

        return res;
    }

    @PostMapping("/seat")
    public BaseResponse insert(@RequestBody Seat seat){
        BaseResponse res = new BaseResponse(ReturnCodeEnum.SUCCESS);

        try{
            res.data = repo.save(seat);

        } catch (Exception e){
            log.error("[insert] ex: {}",e.getMessage());
            res = BaseResponse.EXCEPTION_RESPONSE;
        }

        return res;
    }

    @PutMapping("/seat/{seatID}")
    public BaseResponse insert(@PathVariable String seatID,@RequestBody Seat seat){
        BaseResponse res = new BaseResponse(ReturnCodeEnum.SUCCESS);

        try{
            res.data = repo.save(seat);
        } catch (Exception e){
            log.error("[put] ex: {}",e.getMessage());
            res = BaseResponse.EXCEPTION_RESPONSE;
        }

        return res;
    }

    @DeleteMapping("/seat/{seatID}")
    public BaseResponse delete(@PathVariable Seat.SeatID seatID){
        BaseResponse res = new BaseResponse(ReturnCodeEnum.SUCCESS);

        try{
            repo.deleteById(seatID);
            res.data = true;
        } catch (Exception e){
            log.error("[delete] ex: {}",e.getMessage());
            res = BaseResponse.EXCEPTION_RESPONSE;
        }

        return res;
    }
}
