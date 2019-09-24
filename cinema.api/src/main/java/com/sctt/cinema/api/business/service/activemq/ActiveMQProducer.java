package com.sctt.cinema.api.business.service.activemq;

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

    @Value("${activemq.ticketCancelMinutesBeforeStart}")
    public int cancelMinutesBeforeStart;

    public void sendTicketLogProcessQueue(TicketLog entity, long delaySecond){
        jmsTemplate.convertAndSend(ticketProcessQueue, entity,
                message -> {
                    message.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, delaySecond * 1000);
                    return message;
                });
    }
}
