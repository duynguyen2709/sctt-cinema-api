package com.sctt.cinema.api.util;

import com.sctt.cinema.api.business.entity.response.ShowtimeDTO;

import java.util.Comparator;

public class TimeComparator implements Comparator<ShowtimeDTO.ShowtimeDetailDTO> {

    @Override
    public int compare(ShowtimeDTO.ShowtimeDetailDTO o1, ShowtimeDTO.ShowtimeDetailDTO o2) {
        return Long.compare(o1.timeFrom, o2.timeFrom);
    }
}
