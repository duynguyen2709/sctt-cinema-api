package com.sctt.cinema.api.common.enums;

public enum CacheKeyEnum {

    THEATER,
    ROOM,
    SEAT,
    SHOWTIME,
    TICKET_LOG,
    BUZ_CONFIG,
    MOVIE,
    USER,
    BOOKED_SEAT,

    ROOM_SEAT,
    ALL;

    public static CacheKeyEnum getKey(String type){

        for (CacheKeyEnum key : CacheKeyEnum.values()){
            if (key.name().equalsIgnoreCase(type)) {
                return key;
            }
        }

        return null;
    }
}
