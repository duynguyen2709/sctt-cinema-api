package com.sctt.cinema.api.business.controller.rest;

import com.sctt.cinema.api.business.entity.jpa.Movie;
import com.sctt.cinema.api.business.service.jpa.MovieService;
import com.sctt.cinema.api.business.service.jpa.MovieService;
import com.sctt.cinema.api.common.BaseResponse;
import com.sctt.cinema.api.common.enums.ReturnCodeEnum;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Log4j2
public class MovieRestController {

    @Autowired
    private MovieService service;

    @GetMapping("/movies")
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

    @GetMapping("/movies/{movieID}")
    public BaseResponse findByID(@PathVariable Integer movieID){
        BaseResponse res = new BaseResponse(ReturnCodeEnum.SUCCESS);

        try{
            res.data = service.findById(movieID);
        } catch (Exception e){
            log.error("[findByID] ex: {}",e.getMessage());
            res = BaseResponse.EXCEPTION_RESPONSE;
        }

        return res;
    }

    @PostMapping("/movies")
    public BaseResponse insert(@RequestBody Movie entity){
        BaseResponse res = new BaseResponse(ReturnCodeEnum.SUCCESS);

        try{
            res.data = service.create(entity);
        } catch (Exception e){
            log.error("[insert] ex: {}",e.getMessage());
            res = BaseResponse.EXCEPTION_RESPONSE;
        }

        return res;
    }

    @PutMapping("/movies/{movieID}")
    public BaseResponse update(@PathVariable Integer movieID,@RequestBody Movie entity){
        BaseResponse res = new BaseResponse(ReturnCodeEnum.SUCCESS);

        try{
            entity.movieID = movieID;
            res.data = service.update(entity);
        } catch (Exception e){
            log.error("[update] ex: {}",e.getMessage());
            res = BaseResponse.EXCEPTION_RESPONSE;
        }

        return res;
    }

    @DeleteMapping("/movies/{movieID}")
    public BaseResponse delete(@PathVariable Integer movieID){
        BaseResponse res = new BaseResponse(ReturnCodeEnum.SUCCESS);

        try{
            service.delete(movieID);
            res.data = true;
        } catch (Exception e){
            log.error("[delete] ex: {}",e.getMessage());
            res = BaseResponse.EXCEPTION_RESPONSE;
        }

        return res;
    }
}