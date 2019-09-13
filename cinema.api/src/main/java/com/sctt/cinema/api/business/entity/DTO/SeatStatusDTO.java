package com.sctt.cinema.api.business.entity.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeatStatusDTO implements Serializable {

    public int seatID;

    // ma ghe, vd A1...H1...
    public String seatCode;

    // loai ghe
    // 1 - normal
    // 2 - vip
    public int seatType;

    // 0 - empty
    // 1 - booked
    public int status;
}
