package com.sctt.cinema.api.business.entity.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sctt.cinema.api.business.entity.jpa.Showtime;
import com.sctt.cinema.api.util.DateTimeUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowtimeDTO implements Serializable {

    // <TextDisplay, <Format, List<Detail> >>
    public Map<String, Map<String, List<ShowtimeDetailDTO>>> showtimes = new HashMap<>();

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ShowtimeDetailDTO implements Serializable {
        public int showtimeID;
        public String startTime;

        @JsonIgnore
        public long timeFrom;

        public ShowtimeDetailDTO(Showtime showtime){
            this.showtimeID = showtime.showtimeID;
            this.startTime = DateTimeUtils.getHHmmFromTimestamp(showtime.timeFrom);
            this.timeFrom = showtime.getTimeFrom();
        }
    }
}