package com.sctt.cinema.api.business.service.activemq;

import com.sctt.cinema.api.business.entity.jpa.BookedSeat;
import com.sctt.cinema.api.business.entity.jpa.Showtime;
import com.sctt.cinema.api.business.entity.jpa.TicketLog;
import com.sctt.cinema.api.business.service.jpa.BookedSeatService;
import com.sctt.cinema.api.business.service.jpa.RoomService;
import com.sctt.cinema.api.business.service.jpa.ShowtimeService;
import com.sctt.cinema.api.business.service.jpa.TicketLogService;
import com.sctt.cinema.api.common.ActionResult;
import com.sctt.cinema.api.common.enums.ReturnCodeEnum;
import com.sctt.cinema.api.common.enums.TicketStatusEnum;
import com.sctt.cinema.api.util.GsonUtils;
import lombok.extern.log4j.Log4j2;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class TicketProcessListener {

    @Autowired
    private TicketLogService ticketLogService;

    @Autowired
    private BookedSeatService bookedSeatService;

    @Autowired
    private ShowtimeService showtimeService;

    @JmsListener(destination = "${activemq.ticketProcessQueue}")
    public void consume(TicketLog logEnt) {
        ActionResult aResult = new ActionResult();
        aResult.returnCode = ReturnCodeEnum.INIT.getValue();
        try {
            if (logEnt == null)
                throw new Exception("consume null entity");

            if (logEnt.status == TicketStatusEnum.PAYING.getValue()){
                aResult.step = "ticketLogService.update to CANCELLED";
                logEnt.status = TicketStatusEnum.CANCELLED.getValue();
                ticketLogService.update(logEnt);

                aResult.step = "bookedSeatService.delete(seat.getKey())";
                Showtime showtime = showtimeService.findById(logEnt.showtimeID);
                for (String s: logEnt.getSeatCodes()){
                    BookedSeat seat = new BookedSeat(showtime.roomID, s, logEnt.showtimeID);
                    bookedSeatService.delete(seat.getKey());
                }

                aResult.step = "finish";
                aResult.stepResult = "success";
                aResult.returnCode = ReturnCodeEnum.SUCCESS.getValue();
            }
        } catch (Exception e) {
            log.error("consume logEnt ex", e);
            aResult.stepResult = "failed";
            aResult.returnCode = ReturnCodeEnum.EXCEPTION.getValue();
            aResult.exception = e.getMessage();
        } finally {
            aResult.endTime = System.currentTimeMillis();

            if (aResult.returnCode != ReturnCodeEnum.INIT.getValue())
                log.info(String.format("TicketProcessListener: {%s} | result: {%s}",
                    GsonUtils.toJsonString(logEnt),
                    GsonUtils.toJsonString(aResult)));
        }
    }
}