package com.sctt.cinema.api.business.controller.rest;

import com.sctt.cinema.api.business.entity.jpa.TicketLog;
import com.sctt.cinema.api.business.repository.TicketLogRepository;
import com.sctt.cinema.api.common.BaseResponse;
import com.sctt.cinema.api.common.enums.ReturnCodeEnum;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api")
@Log4j2
public class TicketLogRestController {

    @Autowired
    private TicketLogRepository repo;

    @GetMapping("/ticketLog")
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

    @GetMapping("/ticketLog/{ticketLogID}")
    public BaseResponse findByID(@PathVariable String ticketLogID){
        BaseResponse res = new BaseResponse(ReturnCodeEnum.SUCCESS);

        try{
            res.data = repo.findById(ticketLogID).get();
        } catch (Exception e){
            log.error("[findByID] ex: {}",e.getMessage());
            res = BaseResponse.EXCEPTION_RESPONSE;
        }

        return res;
    }

    @PostMapping("/ticketLog")
    public BaseResponse insert(@RequestBody TicketLog ticketLog){
        BaseResponse res = new BaseResponse(ReturnCodeEnum.SUCCESS);

        try{
            res.data = repo.save(ticketLog);
        } catch (Exception e){
            log.error("[insert] ex: {}",e.getMessage());
            res = BaseResponse.EXCEPTION_RESPONSE;
        }

        return res;
    }

    @PutMapping("/ticketLog/{ticketLogID}")
    public BaseResponse insert(@PathVariable String ticketLogID,@RequestBody TicketLog ticketLog){
        BaseResponse res = new BaseResponse(ReturnCodeEnum.SUCCESS);

        try{
            res.data = repo.save(ticketLog);
        } catch (Exception e){
            log.error("[put] ex: {}",e.getMessage());
            res = BaseResponse.EXCEPTION_RESPONSE;
        }

        return res;
    }

    @DeleteMapping("/ticketLog/{ticketLogID}")
    public BaseResponse delete(@PathVariable String ticketLogID){
        BaseResponse res = new BaseResponse(ReturnCodeEnum.SUCCESS);

        try{
            repo.deleteById(ticketLogID);
            res.data = true;
        } catch (Exception e){
            log.error("[delete] ex: {}",e.getMessage());
            res = BaseResponse.EXCEPTION_RESPONSE;
        }

        return res;
    }
}
