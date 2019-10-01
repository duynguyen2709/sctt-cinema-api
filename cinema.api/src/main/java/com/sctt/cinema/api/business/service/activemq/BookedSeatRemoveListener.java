package com.sctt.cinema.api.business.service.activemq;

import com.sctt.cinema.api.business.entity.jpa.BookedSeat;
import com.sctt.cinema.api.business.service.jpa.BookedSeatService;
import com.sctt.cinema.api.common.ActionResult;
import com.sctt.cinema.api.common.enums.ReturnCodeEnum;
import com.sctt.cinema.api.util.GsonUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service @Log4j2
public class BookedSeatRemoveListener {

    @Autowired
    private BookedSeatService bookedSeatService;

    @JmsListener(destination = "${activemq.bookedSeatRemoveQueue}")
    public void consume(BookedSeat logEnt) {
        ActionResult aResult = new ActionResult();
        aResult.returnCode = ReturnCodeEnum.INIT.getValue();
        try {
            if (logEnt == null) {
                throw new Exception("consume null entity");
            }

            if (bookedSeatService.findById(logEnt.getKey()) != null) {
                bookedSeatService.delete(logEnt.getKey());
                aResult.step = "finish";
                aResult.stepResult = "success";
                aResult.returnCode = ReturnCodeEnum.SUCCESS.getValue();
            }

        } catch (Exception e) {
            log.error("consume logEnt ex {}", e.getMessage());
            aResult.stepResult = "failed";
            aResult.returnCode = ReturnCodeEnum.EXCEPTION.getValue();
            aResult.exception = e.getMessage();
        } finally {
            aResult.endTime = System.currentTimeMillis();
            if (aResult.returnCode != ReturnCodeEnum.INIT.getValue()) {
                log.info(String.format("BookedSeatRemoveListener: {%s} | result: {%s}",
                        GsonUtils.toJsonString(logEnt),
                        GsonUtils.toJsonString(aResult)));
            }
        }
    }
}