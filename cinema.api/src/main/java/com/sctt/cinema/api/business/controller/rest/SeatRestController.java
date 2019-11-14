package com.sctt.cinema.api.business.controller.rest;

import com.sctt.cinema.api.business.entity.jpa.Seat;
import com.sctt.cinema.api.business.entity.request.RoomSeatMappingDTO;
import com.sctt.cinema.api.business.service.jpa.RoomSeatService;
import com.sctt.cinema.api.business.service.jpa.RoomService;
import com.sctt.cinema.api.business.service.jpa.SeatService;
import com.sctt.cinema.api.common.BaseResponse;
import com.sctt.cinema.api.common.enums.ReturnCodeEnum;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@Log4j2
public class SeatRestController {

    @Autowired
    private RoomSeatService roomSeatService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private SeatService seatService;

    @GetMapping("/seats/batch")
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

    @PostMapping("/seats/batch")
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

    @PutMapping("/seats/batch/{roomID}")
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

    @GetMapping("/seats")
    public BaseResponse findAll(@RequestParam(required = false) Integer roomID) {
        BaseResponse res = new BaseResponse(ReturnCodeEnum.SUCCESS);

        try {
            List<Seat> data = seatService.findAll();
            if (roomID != null){
                data = data.stream().filter(c -> c.roomID == roomID).collect(Collectors.toList());
            }
            res.data = data;
        } catch (Exception e) {
            log.error("[findAll] ex: {}", e.getMessage());
            res = BaseResponse.EXCEPTION_RESPONSE;
        }

        return res;
    }

    @PostMapping("/seats")
    public BaseResponse insertSeat(@RequestBody Seat entity) {
        BaseResponse res = new BaseResponse(ReturnCodeEnum.SUCCESS);

        try {
            if (!entity.isValid()){
                return new BaseResponse(ReturnCodeEnum.DATA_NOT_VALID);
            }
            res.data = seatService.create(entity);
        } catch (Exception e) {
            log.error("[insertSeat] ex: {}", e.getMessage());
            res = BaseResponse.EXCEPTION_RESPONSE;
        }

        return res;
    }

    @PutMapping("/seats/{roomID}/{seatCode}")
    public BaseResponse updateSeat(@PathVariable int roomID, @PathVariable String seatCode,
                               @RequestBody Seat entity) {
        BaseResponse res = new BaseResponse(ReturnCodeEnum.SUCCESS);

        try {
            entity.roomID = roomID;
            entity.seatCode = seatCode;
            if (!entity.isValid()){
                return new BaseResponse(ReturnCodeEnum.DATA_NOT_VALID);
            }
            res.data = seatService.update(entity);
        } catch (Exception e) {
            log.error("[updateSeat] ex: {}", e.getMessage());
            res = BaseResponse.EXCEPTION_RESPONSE;
        }

        return res;
    }

    @DeleteMapping("/seats/{roomID}/{seatCode}")
    public BaseResponse deleteSeat(@PathVariable int roomID, @PathVariable String seatCode) {
        BaseResponse res = new BaseResponse(ReturnCodeEnum.SUCCESS);

        try {
            seatService.delete(String.format("%s_%s",roomID,seatCode));
            res.data = true;
        } catch (Exception e) {
            log.error("[deleteSeat] ex: {}", e.getMessage());
            res = BaseResponse.EXCEPTION_RESPONSE;
        }

        return res;
    }
}