package com.sctt.cinema.api.business.service;

import com.sctt.cinema.api.business.entity.jpa.*;
import com.sctt.cinema.api.business.entity.request.OrderDTO;
import com.sctt.cinema.api.business.entity.response.TicketDTO;
import com.sctt.cinema.api.business.service.activemq.ActiveMQProducer;
import com.sctt.cinema.api.business.service.jpa.*;
import com.sctt.cinema.api.common.enums.MovieFormatEnum;
import com.sctt.cinema.api.common.enums.TicketStatusEnum;
import com.sctt.cinema.api.util.DateTimeUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.sctt.cinema.api.business.service.jpa.TicketLogService.CURRENT_TICKET_ID;

@Service
@Log4j2
public class BuzService {

    //<editor-fold defaultstate="collapsed" desc="Services">
    @Autowired
    private ShowtimeService showtimeService;

    @Autowired
    private BuzConfigService buzConfigService;

    @Autowired
    private SeatService seatService;

    @Autowired
    private BookedSeatService bookedSeatService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private TicketLogService ticketLogService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private TheaterService theaterService;

    @Autowired
    private ActiveMQProducer producer;
    //</editor-fold>

    private long calculateTicketPrice(OrderDTO entity) {
        long res = 0;
        Showtime showtime = showtimeService.findById(entity.showtimeID);

        res = calcBySeatType(res, entity);

        // 3D format
        if (showtime.movieFormat == MovieFormatEnum._3D.getValue()) {
            res = calcByFormat(res, entity.seatCodes.size());
        }

        // discount
        res = calcByDiscount(res, entity.seatCodes.size());
        res = calcByUserLevel(res, entity);

        return res;
    }

    private long calcByUserLevel(long res, OrderDTO entity) {
        String userVIP = String.format("%s_%s", "UserAccumulation", "VIPMember");
        String discount = String.format("%s_%s", "TicketPrice", "VIPMemberDiscount");
        String typeCalc = String.format("%s_%s", "TicketPrice", "Type");

        BuzConfig userVIPConf = buzConfigService.findById(userVIP);
        BuzConfig discountConf = buzConfigService.findById(discount);
        BuzConfig typeConf = buzConfigService.findById(typeCalc);

        if (userVIP != null
                && discountConf != null
                && typeConf != null
                && !userVIPConf.buzValue.isEmpty()
                && !discountConf.buzValue.isEmpty()
                && !typeConf.buzValue.isEmpty()){
            long require = Long.parseLong(userVIPConf.buzValue);

            User user = userService.findById(entity.email);
            if (user.totalAccumulation > require){
                int    value = Integer.parseInt(discountConf.buzValue);
                String type  = typeConf.buzValue;

                if (type.equalsIgnoreCase("direct")) {
                    res -= value;
                }
                else if (type.equalsIgnoreCase("percentage")) {
                    res *= (100 - value) / 100;
                }
            }
        }

        return res;
    }

    private long calcBySeatType(long res, OrderDTO entity) {
        Showtime showtime  = showtimeService.findById(entity.showtimeID);
        long     basePrice = movieService.findById(showtime.movieID).baseTicketPrice;

        String priceIncreaseBySeatType = String.format("%s_%s", "TicketPrice", "VIPSeat");
        String increaseType            = String.format("%s_%s", "TicketPrice", "Type");

        BuzConfig priceIncreaseBySeatTypeConf = buzConfigService.findById(priceIncreaseBySeatType);
        BuzConfig increaseTypeConf            = buzConfigService.findById(increaseType);

        for (Object seat : entity.seatCodes) {
            String seatCode = String.valueOf(seat);
            int    seatType = seatService.findById(String.format("%s_%s", entity.roomID, seatCode)).seatType;
            if (seatType == 2) {
                // VIP SEAT
                if (priceIncreaseBySeatTypeConf != null
                        && increaseTypeConf != null
                        && !priceIncreaseBySeatTypeConf.buzValue.isEmpty()
                        && !increaseTypeConf.buzValue.isEmpty())
                {
                    int    value = Integer.parseInt(priceIncreaseBySeatTypeConf.buzValue);
                    String type  = increaseTypeConf.buzValue;

                    if (type.equalsIgnoreCase("direct")) {
                        res += basePrice + value;
                    }
                    else if (type.equalsIgnoreCase("percentage")) {
                        res += basePrice * (100 + value) / 100;
                    }
                }
            } else {
                res += basePrice;
            }
        }
        return res;
    }

    private long calcByDiscount(long res, int size) {
        String discount     = String.format("%s_%s", "TicketPrice", "Discount");
        String increaseType = String.format("%s_%s", "TicketPrice", "Type");

        BuzConfig discountConf     = buzConfigService.findById(discount);
        BuzConfig increaseTypeConf = buzConfigService.findById(increaseType);

        if (discountConf != null
                && increaseTypeConf != null
                && !discountConf.buzValue.isEmpty()
                && !increaseTypeConf.buzValue.isEmpty())
        {
            int    value = Integer.parseInt(discountConf.buzValue);
            String type  = increaseTypeConf.buzValue;

            if (type.equalsIgnoreCase("direct")) {
                res -= size * value;
            }
            else if (type.equalsIgnoreCase("percentage")) {
                res *= (100 - value) / 100;
            }
        }

        return res;
    }

    private long calcByFormat(long res, int size) {
        String priceIncreaseByFormat = String.format("%s_%s", "TicketPrice", "3DMovie");
        String increaseType          = String.format("%s_%s", "TicketPrice", "Type");

        BuzConfig priceIncreaseByFormatConf = buzConfigService.findById(priceIncreaseByFormat);
        BuzConfig increaseTypeConf          = buzConfigService.findById(increaseType);

        if (priceIncreaseByFormatConf != null
                && increaseTypeConf != null
                && !priceIncreaseByFormatConf.buzValue.isEmpty()
                && !increaseTypeConf.buzValue.isEmpty())
        {
            int    value = Integer.parseInt(priceIncreaseByFormatConf.buzValue);
            String type  = increaseTypeConf.buzValue;

            if (type.equalsIgnoreCase("direct")) {
                res += size * value;
            }
            else if (type.equalsIgnoreCase("percentage")) {
                res *= (100 + value) / 100;
            }
        }

        return res;
    }

    private long generateID(long currentID) {
        // id format: YYMMDDxxxx
        // YYMMDD: current date
        // xxxx auto increase
        String oldYYMMDD = "";

        if (currentID != 0) {
            oldYYMMDD = String.valueOf(currentID).substring(0, 6);
        }

        String now = DateTimeUtils.getCurrentYYMMDD();
        return (oldYYMMDD.equalsIgnoreCase(now)) ? (currentID + 1) : Long.parseLong(now + "0001");
    }

    public TicketDTO createTicket(OrderDTO entity) throws Exception {
        TicketLog ticket = new TicketLog();
        ticket.email = entity.email;
        ticket.setSeatCodes(entity.seatCodes);
        ticket.totalPrice = calculateTicketPrice(entity);
        ticket.showtimeID = entity.showtimeID;
        ticket.status = TicketStatusEnum.PAYING.getValue();

        CURRENT_TICKET_ID = generateID(CURRENT_TICKET_ID);
        ticket.ticketID = CURRENT_TICKET_ID;

        ticketLogService.create(ticket);

        bookedSeatService.batchInsert(entity);

        sendTicketProcessingQueue(entity, ticket);

        return convertToDTO(ticket);
    }

    // before xxx minutes from movie start time
    // if customer had not paid
    // then cancel ticket
    private void sendTicketProcessingQueue(OrderDTO entity, TicketLog ticket) throws Exception {
        Showtime showtime = showtimeService.findById(entity.showtimeID);
        long timeStart = showtime.getTimeFrom() - producer.cancelMinutesBeforeStart * 1000 * 60;
        long delaySecond = (timeStart - System.currentTimeMillis()) / 1000;

        //after movie end, remove booked Seats
        long delaySecondEnd = (showtime.getTimeTo() - System.currentTimeMillis()) / 1000;

        if (delaySecond <= 0 || delaySecondEnd <= 0) {
            throw new Exception("[sendTicketProcessingQueue] delaySecond < 0");
        }

        producer.sendTicketLogProcessQueue(ticket, delaySecond);

        for (Object s : entity.seatCodes) {
            BookedSeat seat = new BookedSeat(entity.roomID, String.valueOf(s), entity.showtimeID);
            producer.sendBookedSeatRemoveQueue(seat, delaySecondEnd);
        }
    }

    // convert from TicketLogEntity to TicketDTO
    public TicketDTO convertToDTO(TicketLog ticket) throws Exception{

        Showtime showtime = showtimeService.findById(ticket.showtimeID);
        Room room = roomService.findById(showtime.roomID);
        Theater theater = theaterService.findById(room.theaterID);
        Movie movie = movieService.findById(showtime.movieID);

        TicketDTO dto = new TicketDTO();
        dto.ticketID = ticket.ticketID;
        dto.movieName = movie.movieName;
        dto.poster = movie.imageURL;
        dto.date = DateTimeUtils.parseTimestampToString(showtime.timeFrom.getTime(), "dd-MM-yyyy");
        dto.time = String.format("%s ~ %s",DateTimeUtils.getHHmmFromTimestamp(showtime.timeFrom),
                DateTimeUtils.getHHmmFromTimestamp(showtime.timeTo));
        dto.theaterName = theater.theaterName;
        dto.roomNumber = room.roomNumber + "";
        dto.totalPrice = ticket.totalPrice;
        for (Object s: ticket.getSeatCodes()){
            if (!dto.seatCodes.isEmpty())
                dto.seatCodes += ", ";

            dto.seatCodes += String.valueOf(s);
        }
        dto.extraInfo = ticket.extraInfo;

        return dto;
    }
}