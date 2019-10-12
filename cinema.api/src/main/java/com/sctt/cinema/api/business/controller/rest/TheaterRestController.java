package com.sctt.cinema.api.business.controller.rest;

import com.sctt.cinema.api.business.entity.jpa.Theater;
import com.sctt.cinema.api.business.service.jpa.TheaterService;
import com.sctt.cinema.api.common.BaseResponse;
import com.sctt.cinema.api.common.enums.ReturnCodeEnum;
import com.sctt.cinema.api.util.GsonUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Log4j2
public class TheaterRestController {

    @Autowired
    private TheaterService service;

    @GetMapping("/theaters")
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

    @GetMapping("/theaters/{theaterID}")
    public BaseResponse findByID(@PathVariable Integer theaterID){
        BaseResponse res = new BaseResponse(ReturnCodeEnum.SUCCESS);

        try{
            res.data = service.findById(theaterID);
            if (res.data == null)
                res = new BaseResponse(ReturnCodeEnum.THEATER_NOT_FOUND);
        } catch (Exception e){
            log.error("[findByID] ex: {}",e.getMessage());
            res = BaseResponse.EXCEPTION_RESPONSE;
        }

        return res;
    }

    @PostMapping("/theaters")
    public BaseResponse insert(@RequestBody Theater theater){
        BaseResponse res = new BaseResponse(ReturnCodeEnum.SUCCESS);

        try{
            if (!theater.isValid()){
                return new BaseResponse(ReturnCodeEnum.DATA_NOT_VALID);
            }

            res.data = service.create(theater);
        } catch (Exception e){
            log.error("[insert] ex: {}",e.getMessage());
            res = BaseResponse.EXCEPTION_RESPONSE;
        }

        return res;
    }

    @PutMapping("/theaters/{theaterID}")
    public BaseResponse update(@PathVariable Integer theaterID,@RequestBody Theater theater){
        BaseResponse res = new BaseResponse(ReturnCodeEnum.SUCCESS);
        log.info(GsonUtils.toJsonString(theater));
        try{
            theater.theaterID = theaterID;

            if (!theater.isValid()){
                return new BaseResponse(ReturnCodeEnum.DATA_NOT_VALID);
            }
            res.data = service.update(theater);
        } catch (Exception e){
            log.error("[update] ex: {}",e.getMessage());
            res = BaseResponse.EXCEPTION_RESPONSE;
        }

        return res;
    }

    @DeleteMapping("/theaters/{theaterID}")
    public BaseResponse delete(@PathVariable Integer theaterID){
        BaseResponse res = new BaseResponse(ReturnCodeEnum.SUCCESS);

        try{
            service.delete(theaterID);
            res.data = true;
        } catch (Exception e){
            log.error("[delete] ex: {}",e.getMessage());
            res = BaseResponse.EXCEPTION_RESPONSE;
        }

        return res;
    }
}