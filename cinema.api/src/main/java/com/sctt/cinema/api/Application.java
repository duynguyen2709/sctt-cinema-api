package com.sctt.cinema.api;

import com.sctt.cinema.api.business.entity.config.HazelCastConfig;
import com.sctt.cinema.api.business.service.HazelCastService;
import com.sctt.cinema.api.business.service.jpa.TheaterService;
import com.sctt.cinema.api.util.HazelCastUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.event.EventListener;

@SpringBootApplication
@Log4j2
@ServletComponentScan
public class Application {

	@Value("${server.port}")
	private int port;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}


	@EventListener(ApplicationReadyEvent.class)
	public void Init(){
		try {

			log.info("=============== Application Initializing... ===============");

			log.info("Application Started on Port {}", port);
			log.info("=============== Application Init Done ===============");

		} catch (Exception e){
			log.error("Application Init Failed: " + e.getMessage(),e);
			System.exit(0);
		}
	}
}
