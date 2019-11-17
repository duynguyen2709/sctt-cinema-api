package com.sctt.cinema.api.business.controller.client;

import com.sctt.cinema.api.business.entity.jpa.*;
import com.sctt.cinema.api.business.entity.request.OrderDTO;
import com.sctt.cinema.api.business.entity.response.TicketDTO;
import com.sctt.cinema.api.business.service.BuzService;
import com.sctt.cinema.api.business.service.activemq.ActiveMQProducer;
import com.sctt.cinema.api.business.service.jpa.*;
import com.sctt.cinema.api.common.BaseResponse;
import com.sctt.cinema.api.common.enums.ReturnCodeEnum;
import com.sctt.cinema.api.common.enums.TicketStatusEnum;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.sctt.cinema.api.business.service.jpa.TicketLogService.CURRENT_TICKET_ID;

@RestController
@RequestMapping("/client/private")
@Log4j2
public class ClientPrivateController {

    //<editor-fold defaultstate="collapsed" desc="Services">
    @Autowired
    private ShowtimeService showtimeService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private UserService userService;

    @Autowired
    private BookedSeatService bookedSeatService;

    @Autowired
    private TicketLogService ticketLogService;

    @Autowired
    private BuzService buzService;

    //</editor-fold>

    @PostMapping("/payticket/{ticketID}")
    public BaseResponse payOrder(@PathVariable long ticketID){
        BaseResponse res = new BaseResponse(ReturnCodeEnum.SUCCESS);

        try{
            TicketLog ticket = ticketLogService.findById(ticketID);
            if (ticket == null){
                res = new BaseResponse(ReturnCodeEnum.TICKET_NOT_FOUND);
                return res;
            }

            if (ticket.status == TicketStatusEnum.PAYING.getValue()) {
                ticket.status = TicketStatusEnum.SUCCESS.getValue();
                ticket = ticketLogService.update(ticket);

                //only update when user had scanned ticket to pay
                User user = userService.findById(ticket.email);
                user.totalAccumulation += ticket.totalPrice;
                userService.update(user);

                res.data = buzService.convertToDTO(ticket);

            } else if (ticket.status == TicketStatusEnum.SUCCESS.getValue()) {
                res = new BaseResponse(ReturnCodeEnum.TICKET_PAID);

            } else if (ticket.status == TicketStatusEnum.CANCELLED.getValue()) {
                res = new BaseResponse(ReturnCodeEnum.TICKET_CANCELLED);
            }

        } catch (Exception e){
            log.error("[payOrder] ex: {}",e.getMessage(), e);
            res = BaseResponse.EXCEPTION_RESPONSE;
        }

        return res;
    }

    @PostMapping("/createticket")
    public BaseResponse createOrder(@RequestBody OrderDTO entity){
        BaseResponse res = new BaseResponse(ReturnCodeEnum.SUCCESS);

        try{
            // validate data
            Showtime showtime = showtimeService.findById(entity.showtimeID);
            if (showtime == null){
                res = new BaseResponse(ReturnCodeEnum.SHOWTIME_NOT_FOUND);
                return res;
            }

            entity.roomID = showtime.roomID;

            if (userService.findById(entity.email) == null){
                res = new BaseResponse(ReturnCodeEnum.USER_NOT_FOUND);
                return res;
            }

            //check booked seat
            for (Object seat: entity.seatCodes){
                String key = String.format("%s_%s_%s",entity.roomID, String.valueOf(seat), entity.showtimeID);
                if (bookedSeatService.findById(key) != null){
                    res = new BaseResponse(ReturnCodeEnum.SEAT_NOT_EMPTY);
                    return res;
                }
            }

            res.data = buzService.createTicket(entity);

        } catch (Exception e){
            log.error("[createOrder] ex: {}",e.getMessage(), e);
            res = BaseResponse.EXCEPTION_RESPONSE;
        }

        return res;
    }

    @RequestMapping(method = {RequestMethod.GET,RequestMethod.POST},value = "/history/{email}")
    public BaseResponse getHistory(@PathVariable String email){
        BaseResponse res = new BaseResponse(ReturnCodeEnum.SUCCESS);

        try{
            if (userService.findById(email) == null){
                res = new BaseResponse(ReturnCodeEnum.USER_NOT_FOUND);
                return res;
            }
            List<TicketLog> listTicket = ticketLogService.findAll()
                    .stream()
                    .filter(t -> t.email.equalsIgnoreCase(email))
                    .collect(Collectors.toList());

            List<TicketDTO> listDTO = new ArrayList<>();
            for (TicketLog t : listTicket)
                listDTO.add(buzService.convertToDTO(t));

            res.data = listDTO;
        } catch (Exception e){
            log.error("[getHistory] ex: {}",e.getMessage(), e);
            res = BaseResponse.EXCEPTION_RESPONSE;
        }

        return res;
    }

}