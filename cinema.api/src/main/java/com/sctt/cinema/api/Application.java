package com.sctt.cinema.api;

import com.sctt.cinema.api.business.config.ActiveMQConfig;
import com.sctt.cinema.api.business.service.activemq.ActiveMQProducer;
import com.sctt.cinema.api.util.GsonUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.event.EventListener;

import javax.annotation.PreDestroy;

@SpringBootApplication
@Log4j2
@ServletComponentScan
public class Application {

	@Value("${server.port}")
	private int port;

	@Autowired
	private ActiveMQConfig amqConf;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void Init(){
		try {

			log.info("=============== Application Initializing... ===============");

			log.info(String.format("\nAMQConf: %s",GsonUtils.toJsonString(amqConf)));
			ActiveMQProducer.jmsTemplate = amqConf.jmsTemplate();

			log.info("Application Started on Port {}", port);
			log.info("=============== Application Init Done ===============");

		} catch (Exception e){
			log.error("Application Init Failed: " + e.getMessage());
			System.exit(0);
		}
	}

	@PreDestroy
	public void onExit() {
		log.info("############ Application Stopped ###############");
	}
}
