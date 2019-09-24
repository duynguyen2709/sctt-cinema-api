package com.sctt.cinema.api.business.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.DeliveryMode;

/**
 * @author truongnx10
 */
@Getter
@Setter
@ToString
@EnableJms
@Component
public class ActiveMQConfig {

    @Value("${activemq.brokerURL}")
    private String brokerURL;

    @Value("${activemq.timeToLive}")
    private long timeToLive;

    @Value("${activemq.numOfConsumer}")
    private String numOfConsumer;

    @Getter
    @Value("${activemq.ticketProcessQueue}")
    private String ticketProcessQueue;

    @Bean
    public ActiveMQConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(brokerURL);
        connectionFactory.setTrustAllPackages(true);
        return connectionFactory;
    }

    @Bean
    public JmsTemplate jmsTemplate() {
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(connectionFactory());
        template.setExplicitQosEnabled(true);
        template.setDeliveryMode(DeliveryMode.PERSISTENT);
        template.setTimeToLive(timeToLive);
        return template;
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setConcurrency(numOfConsumer);
        factory.setPubSubDomain(false); // true: using jms topic, false: using jms queue
        return factory;
    }
}