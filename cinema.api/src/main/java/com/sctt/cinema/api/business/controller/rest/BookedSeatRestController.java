package com.sctt.cinema.api.business.controller.rest;

import com.sctt.cinema.api.business.entity.jpa.BookedSeat;
import com.sctt.cinema.api.business.repository.BookedSeatRepository;
import com.sctt.cinema.api.common.BaseResponse;
import com.sctt.cinema.api.common.enums.ReturnCodeEnum;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api")
@Log4j2
public class BookedSeatRestController {

    @Autowired
    private BookedSeatRepository repo;

    @GetMapping("/theater")
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

    /*@GetMapping("/bookedSeat/{bookedSeatID}")
    public BaseResponse findByID(@PathVariable int bookedSeatID){
        BaseResponse res = new BaseResponse(ReturnCodeEnum.SUCCESS);

        try{
            res.data = repo.findById(bookedSeatID).get();
        } catch (Exception e){
            log.error("[findByID] ex: {}",e.getMessage());
            res = BaseResponse.EXCEPTION_RESPONSE;
        }

        return res;
    }*/

    @PostMapping("/bookedSeat")
    public BaseResponse insert(@RequestBody BookedSeat bookedSeat){
        BaseResponse res = new BaseResponse(ReturnCodeEnum.SUCCESS);

        try{
            res.data = repo.save(bookedSeat);
        } catch (Exception e){
            log.error("[insert] ex: {}",e.getMessage());
            res = BaseResponse.EXCEPTION_RESPONSE;
        }

        return res;
    }

    @PutMapping("/bookedSeat/{bookedSeatID}")
    public BaseResponse insert(@PathVariable String bookedSeatID,@RequestBody BookedSeat bookedSeat){
        BaseResponse res = new BaseResponse(ReturnCodeEnum.SUCCESS);

        try{
            res.data = repo.save(bookedSeat);
        } catch (Exception e){
            log.error("[put] ex: {}",e.getMessage());
            res = BaseResponse.EXCEPTION_RESPONSE;
        }

        return res;
    }

   /* @DeleteMapping("/bookedSeat/{bookedSeatID}")
    public BaseResponse delete(@PathVariable int bookedSeatID){
        BaseResponse res = new BaseResponse(ReturnCodeEnum.SUCCESS);

        try{
            repo.deleteById(bookedSeatID);
            res.data = true;
        } catch (Exception e){
            log.error("[delete] ex: {}",e.getMessage());
            res = BaseResponse.EXCEPTION_RESPONSE;
        }

        return res;
    }*/
}
