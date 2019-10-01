package com.sctt.cinema.api.business.controller.rest;

import com.sctt.cinema.api.business.entity.jpa.Room;
import com.sctt.cinema.api.business.service.jpa.RoomService;
import com.sctt.cinema.api.business.service.jpa.RoomService;
import com.sctt.cinema.api.common.BaseResponse;
import com.sctt.cinema.api.common.enums.ReturnCodeEnum;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Log4j2
public class RoomRestController {

    @Autowired
    private RoomService service;

    @GetMapping("/rooms")
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

    @GetMapping("/rooms/{roomID}")
    public BaseResponse findByID(@PathVariable Integer roomID){
        BaseResponse res = new BaseResponse(ReturnCodeEnum.SUCCESS);

        try{
            res.data = service.findById(roomID);
            if (res.data == null)
                res = new BaseResponse(ReturnCodeEnum.ROOM_NOT_FOUND);
        } catch (Exception e){
            log.error("[findByID] ex: {}",e.getMessage());
            res = BaseResponse.EXCEPTION_RESPONSE;
        }

        return res;
    }

    @PostMapping("/rooms")
    public BaseResponse insert(@RequestBody Room entity){
        BaseResponse res = new BaseResponse(ReturnCodeEnum.SUCCESS);

        try{
            if (!entity.isValid()){
                return new BaseResponse(ReturnCodeEnum.DATA_NOT_VALID);
            }
            res.data = service.create(entity);
        } catch (Exception e){
            log.error("[insert] ex: {}",e.getMessage());
            res = BaseResponse.EXCEPTION_RESPONSE;
        }

        return res;
    }

    @PutMapping("/rooms/{roomID}")
    public BaseResponse update(@PathVariable Integer roomID,@RequestBody Room entity){
        BaseResponse res = new BaseResponse(ReturnCodeEnum.SUCCESS);

        try{
            entity.roomID = roomID;
            if (!entity.isValid()){
                return new BaseResponse(ReturnCodeEnum.DATA_NOT_VALID);
            }
            res.data = service.update(entity);
        } catch (Exception e){
            log.error("[update] ex: {}",e.getMessage());
            res = BaseResponse.EXCEPTION_RESPONSE;
        }

        return res;
    }

    @DeleteMapping("/rooms/{roomID}")
    public BaseResponse delete(@PathVariable Integer roomID){
        BaseResponse res = new BaseResponse(ReturnCodeEnum.SUCCESS);

        try{
            service.delete(roomID);
            res.data = true;
        } catch (Exception e){
            log.error("[delete] ex: {}",e.getMessage());
            res = BaseResponse.EXCEPTION_RESPONSE;
        }

        return res;
    }
}