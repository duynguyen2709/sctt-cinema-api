package com.sctt.cinema.api.business.controller.rest;

import com.sctt.cinema.api.business.entity.request.RoomSeatMappingDTO;
import com.sctt.cinema.api.business.service.jpa.RoomSeatService;
import com.sctt.cinema.api.business.service.jpa.RoomService;
import com.sctt.cinema.api.common.BaseResponse;
import com.sctt.cinema.api.common.enums.ReturnCodeEnum;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Log4j2
public class SeatRestController {

    @Autowired
    private RoomSeatService roomSeatService;

    @Autowired
    private RoomService roomService;

    @GetMapping("/seats")
    public BaseResponse findByRoomID(@RequestParam(required = false) Integer roomID) {
        BaseResponse res = new BaseResponse(ReturnCodeEnum.SUCCESS);

        try {
            if (roomID == null || roomService.findById(roomID) == null) {
                res = new BaseResponse(ReturnCodeEnum.ROOM_NOT_FOUND);
                return res;
            }

            res.data = roomSeatService.findById(roomID);
        } catch (Exception e) {
            log.error("[findByRoomID] ex: {}", e.getMessage());
            res = BaseResponse.EXCEPTION_RESPONSE;
        }

        return res;
    }

    @PostMapping("/seats")
    public BaseResponse insert(@RequestBody RoomSeatMappingDTO entity) {
        BaseResponse res = new BaseResponse(ReturnCodeEnum.SUCCESS);

        try {
            if (roomSeatService.create(entity) == null){
                throw new Exception();
            }
            res.data = true;
        } catch (Exception e) {
            log.error("[insert] ex: {}", e.getMessage());
            res = BaseResponse.EXCEPTION_RESPONSE;
        }

        return res;
    }

    @PutMapping("/seats/{roomID}")
    public BaseResponse update(@RequestBody RoomSeatMappingDTO entity) {
        BaseResponse res = new BaseResponse(ReturnCodeEnum.SUCCESS);

        try {
            if (roomSeatService.update(entity) == null){
                throw new Exception();
            }
            res.data = true;
        } catch (Exception e) {
            log.error("[update] ex: {}", e.getMessage());
            res = BaseResponse.EXCEPTION_RESPONSE;
        }

        return res;
    }
}