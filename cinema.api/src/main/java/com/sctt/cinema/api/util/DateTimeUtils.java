package com.sctt.cinema.api.util;

import lombok.extern.log4j.Log4j2;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Log4j2
public class DateTimeUtils {
    private static final String HHmm_FORMAT = "HH:mm";

    //return HH:mm from timestamp
    public static String getHHmmFromTimestamp(long time){

        if (time < 0){
            log.error("getHHmmFromTimestamp time < 0");
            return "";
        }

        String timeStamp = new SimpleDateFormat(HHmm_FORMAT).format(new Timestamp(time));
        return timeStamp;
    }
}
