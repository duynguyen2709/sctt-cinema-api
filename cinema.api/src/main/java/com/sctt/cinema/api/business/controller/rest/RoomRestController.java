package com.sctt.cinema.api.business.controller.rest;

import com.sctt.cinema.api.business.entity.jpa.Room;
import com.sctt.cinema.api.business.repository.RoomRepository;
import com.sctt.cinema.api.common.BaseResponse;
import com.sctt.cinema.api.common.enums.ReturnCodeEnum;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api")
@Log4j2
public class RoomRestController {

    @Autowired
    private RoomRepository repo;

    @GetMapping("/room")
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

    @GetMapping("/room/{roomID}")
    public BaseResponse findByID(@PathVariable int roomID){
        BaseResponse res = new BaseResponse(ReturnCodeEnum.SUCCESS);

        try{
            res.data = repo.findById(roomID).get();
        } catch (Exception e){
            log.error("[findByID] ex: {}",e.getMessage());
            res = BaseResponse.EXCEPTION_RESPONSE;
        }

        return res;
    }

    @PostMapping("/room")
    public BaseResponse insert(@RequestBody Room room){
        BaseResponse res = new BaseResponse(ReturnCodeEnum.SUCCESS);

        try{
            res.data = repo.save(room);
        } catch (Exception e){
            log.error("[insert] ex: {}",e.getMessage());
            res = BaseResponse.EXCEPTION_RESPONSE;
        }

        return res;
    }

    @PutMapping("/room/{roomID}")
    public BaseResponse insert(@PathVariable String roomID,@RequestBody Room room){
        BaseResponse res = new BaseResponse(ReturnCodeEnum.SUCCESS);

        try{
            res.data = repo.save(room);
        } catch (Exception e){
            log.error("[put] ex: {}",e.getMessage());
            res = BaseResponse.EXCEPTION_RESPONSE;
        }

        return res;
    }

    @DeleteMapping("/room/{roomID}")
    public BaseResponse delete(@PathVariable int roomID){
        BaseResponse res = new BaseResponse(ReturnCodeEnum.SUCCESS);

        try{
            repo.deleteById(roomID);
            res.data = true;
        } catch (Exception e){
            log.error("[delete] ex: {}",e.getMessage());
            res = BaseResponse.EXCEPTION_RESPONSE;
        }

        return res;
    }
}
