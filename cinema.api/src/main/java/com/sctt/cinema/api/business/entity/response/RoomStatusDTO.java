package com.sctt.cinema.api.business.entity.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomStatusDTO implements Serializable {

    public int showtimeID;

    public int roomID;

    public long basePrice;

    public long VIPPrice;

    public SeatStatusDTO[][] seats;
}
