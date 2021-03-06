package com.sctt.cinema.api.business.service.activemq;

import com.sctt.cinema.api.business.entity.jpa.BookedSeat;
import com.sctt.cinema.api.business.entity.jpa.TicketLog;
import org.apache.activemq.ScheduledMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class ActiveMQProducer {

    public static JmsTemplate jmsTemplate;

    @Value("${activemq.ticketProcessQueue}")
    private String ticketProcessQueue;

    @Value("${activemq.bookedSeatRemoveQueue}")
    private String bookedSeatRemoveQueue;

    public void sendTicketLogProcessQueue(TicketLog entity, long delaySecond){
        jmsTemplate.convertAndSend(ticketProcessQueue, entity,
                message -> {
                    message.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, delaySecond * 1000);
                    return message;
                });
    }

    public void sendBookedSeatRemoveQueue(BookedSeat entity, long delaySecond){
        jmsTemplate.convertAndSend(bookedSeatRemoveQueue, entity,
                message -> {
                    message.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, delaySecond * 1000);
                    return message;
                });
    }
}
