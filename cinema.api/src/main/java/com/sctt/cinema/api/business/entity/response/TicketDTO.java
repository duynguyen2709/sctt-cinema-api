package com.sctt.cinema.api.business.entity.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class TicketDTO implements Serializable {
    public long   ticketID;
    public String movieName;
    public String poster;
    public String date;
    public String time;
    public String theaterName;
    public String roomNumber;
    public String seatCodes = "";
    public long   totalPrice;
    public String extraInfo;
}
