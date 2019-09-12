package com.sctt.cinema.api.business.entity.DTO;

import com.sctt.cinema.api.business.entity.jpa.Showtime;
import com.sctt.cinema.api.util.DateTimeUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowtimeDetailDTO implements Serializable {
    public int showtimeID;
    public String startTime;

    public ShowtimeDetailDTO(Showtime showtime){
        this.showtimeID = showtime.showtimeID;
        this.startTime = DateTimeUtils.getHHmmFromTimestamp(showtime.timeFrom);
    }
}
