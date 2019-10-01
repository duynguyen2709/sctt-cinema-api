package com.sctt.cinema.api.business.controller.client;

import com.sctt.cinema.api.business.entity.jpa.*;
import com.sctt.cinema.api.business.entity.request.OrderDTO;
import com.sctt.cinema.api.business.service.activemq.ActiveMQProducer;
import com.sctt.cinema.api.business.service.jpa.*;
import com.sctt.cinema.api.common.BaseResponse;
import com.sctt.cinema.api.common.enums.MovieFormatEnum;
import com.sctt.cinema.api.common.enums.ReturnCodeEnum;
import com.sctt.cinema.api.common.enums.TicketStatusEnum;
import com.sctt.cinema.api.util.BuzUtils;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

import static com.sctt.cinema.api.business.service.jpa.TicketLogService.CURRENT_TICKET_ID;

@RestController
@RequestMapping("/client/private")
@Log4j2
public class ClientPrivateController {

    //<editor-fold defaultstate="collapsed" desc="Services">
    @Autowired
    private ShowtimeService showtimeService;

    @Autowired
    private BuzConfigService buzConfigService;

    @Autowired
    private SeatService seatService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private UserService userService;

    @Autowired
    private BookedSeatService bookedSeatService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private TicketLogService ticketLogService;

    @Autowired
    private ActiveMQProducer producer;

    //</editor-fold>

    @PostMapping("/payorder/{ticketID}")
    public BaseResponse payOrder(@PathVariable long ticketID){
        BaseResponse res = new BaseResponse(ReturnCodeEnum.SUCCESS);

        try{
            TicketLog ticket = ticketLogService.findById(ticketID);
            if (ticket == null){
                res = new BaseResponse(ReturnCodeEnum.TICKET_NOT_FOUND);
                return res;
            }

            ticket.status = TicketStatusEnum.SUCCESS.getValue();
            ticketLogService.update(ticket);

            //only update when user had scanned ticket to pay
            User user = userService.findById(ticket.email);
            user.totalAccumulation += ticket.totalPrice;
            userService.update(user);

            res.data = true;
        } catch (Exception e){
            log.error("[payOrder] ex: {}",e.getMessage());
            res = BaseResponse.EXCEPTION_RESPONSE;
        }

        return res;
    }

    @PostMapping("/createorder")
    public BaseResponse createOrder(@RequestBody OrderDTO entity){
        BaseResponse res = new BaseResponse(ReturnCodeEnum.SUCCESS);

        try{
            // validate data
            if (showtimeService.findById(entity.showtimeID) == null){
                res = new BaseResponse(ReturnCodeEnum.SHOWTIME_NOT_FOUND);
                return res;
            }

            if (userService.findById(entity.email) == null){
                res = new BaseResponse(ReturnCodeEnum.USER_NOT_FOUND);
                return res;
            }

            if (roomService.findById(entity.roomID) == null){
                res = new BaseResponse(ReturnCodeEnum.ROOM_NOT_FOUND);
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

            res.data = createTicket(entity);

        } catch (Exception e){
            log.error("[createOrder] ex: {}",e.getMessage());
            res = BaseResponse.EXCEPTION_RESPONSE;
        }

        return res;
    }

    private MiniTicketDTO createTicket(OrderDTO entity) throws Exception {
        TicketLog ticket = new TicketLog();
        ticket.email = entity.email;
        ticket.setSeatCodes(entity.seatCodes);
        ticket.totalPrice = calculateTicketPrice(entity);
        ticket.showtimeID = entity.showtimeID;
        ticket.status = TicketStatusEnum.PAYING.getValue();

        CURRENT_TICKET_ID = BuzUtils.generateID(CURRENT_TICKET_ID);
        ticket.ticketID = CURRENT_TICKET_ID;

        ticketLogService.create(ticket);

        bookedSeatService.batchInsert(entity);

        sendTicketProcessQueue(entity, ticket);

        return new MiniTicketDTO(ticket.ticketID);
    }

    private void sendTicketProcessQueue(OrderDTO entity, TicketLog ticket) throws Exception {
        Showtime showtime = showtimeService.findById(entity.showtimeID);
        long timeStart = showtime.getTimeFrom() - producer.cancelMinutesBeforeStart * 1000 * 60;
        long delaySecond = (timeStart - System.currentTimeMillis()) / 1000;
        long delaySecondEnd = (showtime.getTimeTo() - System.currentTimeMillis()) / 1000;

        if (delaySecond <= 0){
            throw new Exception("[sendTicketProcessQueue] delaySecond < 0");
        }

        producer.sendTicketLogProcessQueue(ticket, delaySecond);

        for (Object s : entity.seatCodes){
            BookedSeat seat = new BookedSeat(entity.roomID, String.valueOf(s), entity.showtimeID);
            producer.sendBookedSeatRemoveQueue(seat, delaySecondEnd);
        }
    }

    private long calculateTicketPrice(OrderDTO entity) {
        long res = 0;
        Showtime showtime = showtimeService.findById(entity.showtimeID);
        long basePrice = movieService.findById(showtime.movieID).baseTicketPrice;

        String priceIncreaseByFormat = String.format("%s_%s","TicketPrice","3DMovie");
        String priceIncreaseBySeatType = String.format("%s_%s","TicketPrice","VIPSeat");
        String discount = String.format("%s_%s","TicketPrice","Discount");
        String increaseType = String.format("%s_%s","TicketPrice","Type");

        BuzConfig priceIncreaseByFormatConf = buzConfigService.findById(priceIncreaseByFormat);
        BuzConfig priceIncreaseBySeatTypeConf = buzConfigService.findById(priceIncreaseBySeatType);
        BuzConfig discountConf = buzConfigService.findById(discount);
        BuzConfig increaseTypeConf = buzConfigService.findById(increaseType);

        for (Object seat : entity.seatCodes){
            String seatCode = String.valueOf(seat);
            int seatType = seatService.findById(String.format("%s_%s", entity.roomID, seatCode)).seatType;
            if (seatType == 2){
                // VIP SEAT
                if (priceIncreaseBySeatTypeConf != null && increaseTypeConf != null &&
                        !priceIncreaseBySeatTypeConf.buzValue.isEmpty() && !increaseTypeConf.buzValue.isEmpty()){
                    int value = Integer.parseInt(priceIncreaseBySeatTypeConf.buzValue);
                    String type = increaseTypeConf.buzValue;

                    if (type.equalsIgnoreCase("direct")){
                        res += basePrice + value;
                    } else if (type.equalsIgnoreCase("percentage")){
                        res += basePrice * (100 + value) / 100;
                    }
                }
            } else {
                res += basePrice;
            }
        }

        // 3D format
        if (showtime.movieFormat == MovieFormatEnum._3D.getValue()){
            if (priceIncreaseByFormatConf != null && increaseTypeConf != null &&
                    !priceIncreaseByFormatConf.buzValue.isEmpty() && !increaseTypeConf.buzValue.isEmpty()){
                int value = Integer.parseInt(priceIncreaseByFormatConf.buzValue);
                String type = increaseTypeConf.buzValue;

                if (type.equalsIgnoreCase("direct")){
                    res += entity.seatCodes.size() * value;
                } else if (type.equalsIgnoreCase("percentage")){
                    res *= (100 + value) / 100;
                }
            }
        }

        if (discountConf != null && increaseTypeConf != null &&
                !discountConf.buzValue.isEmpty() && !increaseTypeConf.buzValue.isEmpty()){
            int value = Integer.parseInt(discountConf.buzValue);
            String type = increaseTypeConf.buzValue;

            if (type.equalsIgnoreCase("direct")){
                res -= entity.seatCodes.size() * value;
            } else if (type.equalsIgnoreCase("percentage")){
                res *= (100 - value) / 100;
            }
        }

        return res;
    }

    @RequiredArgsConstructor
    @AllArgsConstructor
    private class MiniTicketDTO implements Serializable {
        public long ticketID;
    }
}