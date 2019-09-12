package com.sctt.cinema.api;

import com.sctt.cinema.api.business.entity.DTO.ShowtimeByFormatDTO;
import com.sctt.cinema.api.business.entity.DTO.ShowtimeDTO;
import com.sctt.cinema.api.business.entity.DTO.ShowtimeDetailDTO;
import com.sctt.cinema.api.business.entity.jpa.TicketLog;
import com.sctt.cinema.api.business.repository.TicketLogRepository;
import com.sctt.cinema.api.util.DateTimeUtils;
import com.sctt.cinema.api.util.GsonUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.Arrays;

@SpringBootApplication
@Log4j2
public class Application {

	@Value("${server.port}")
	private int port;

	@Autowired
	private TicketLogRepository repo;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void Init(){
		try {

			log.info("=============== Application Initializing... ===============");

			log.info("Application Started on Port {}", port);

			TicketLog dto = new TicketLog();
			dto.ticketID = "2";
			dto.customerID = "123";
			dto.showtimeID = 5;
			dto.totalPrice = 100L;
			dto.seatCodes = GsonUtils.toJsonString(Arrays.asList("A2","V3"));

			repo.save(dto);

			log.info(GsonUtils.toJsonString(repo.findById("2").get()));

			log.info("=============== Application Init Done ===============");

		} catch (Exception e){
			log.error("Application Init Failed: " + e.getMessage(),e);
			System.exit(0);
		}
	}
}
