package com.sctt.cinema.api.business.service;

import com.sctt.cinema.api.business.entity.jpa.BuzConfig;
import com.sctt.cinema.api.business.entity.jpa.Showtime;
import com.sctt.cinema.api.business.entity.jpa.TicketLog;
import com.sctt.cinema.api.business.service.activemq.ActiveMQProducer;
import com.sctt.cinema.api.business.service.jpa.BuzConfigService;
import com.sctt.cinema.api.business.service.jpa.ShowtimeService;
import com.sctt.cinema.api.business.service.jpa.TicketLogService;
import com.sctt.cinema.api.common.enums.TicketStatusEnum;
import com.sctt.cinema.api.util.GsonUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class UpdateTicketStatusService {

    @Autowired
    private TicketLogService ticketLogService;

    @Autowired
    private ShowtimeService showtimeService;

    @Autowired
    private ActiveMQProducer producer;

    @Autowired
    private BuzConfigService buzConfigService;

    @Order(Ordered.LOWEST_PRECEDENCE)
    @EventListener(ApplicationReadyEvent.class)
    public void autoUpdate(){
        List<TicketLog> pendingTickets = ticketLogService.findAll()
                .stream().filter(c -> c.status == TicketStatusEnum.PAYING.getValue())
                .collect(Collectors.toList());

        if (pendingTickets.isEmpty())
            return;

        String key = String.format("%s_%s", "Ticket", "CancelMinutesBeforeStart");
        BuzConfig conf = buzConfigService.findById(key);
        int minutes = Integer.parseInt(conf.buzValue);

        for (TicketLog ticket: pendingTickets){
            Showtime showtime = showtimeService.findById(ticket.showtimeID);

            long timeStart = showtime.getTimeFrom() - minutes * 1000 * 60;
            if (System.currentTimeMillis() >= timeStart){
                producer.sendTicketLogProcessQueue(ticket,0);

                log.info("### Auto Update Ticket " + GsonUtils.toJsonString(ticket));
            }
        }
    }
}