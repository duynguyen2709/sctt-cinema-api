package com.sctt.cinema.api.util;

import com.sctt.cinema.api.common.enums.MovieStatusEnum;
import lombok.extern.log4j.Log4j2;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Log4j2
public class DateTimeUtils {
    private static final String HHmm_FORMAT = "HH:mm";
    private static final String YYMMDD_FORMAT = "yyMMdd";
    //return HH:mm from timestamp
    public static String getHHmmFromTimestamp(Timestamp time){

        if (time.getTime() < 0){
            log.error("getHHmmFromTimestamp time < 0");
            return "";
        }

        String timeStamp = new SimpleDateFormat(HHmm_FORMAT).format(time);
        return timeStamp;
    }

    public static String getCurrentYYMMDD(){
        return parseTimestampToString(System.currentTimeMillis(), YYMMDD_FORMAT);
    }

    public static Date parseStringToDate(String date, String format){
        Date res = null;
        try {
            res = new SimpleDateFormat(format).parse(date);
        }
        catch (ParseException e) {
            log.error("[parseStringToDate] ex {}", e.getMessage());
        }
        return res;
    }

    public static String parseTimestampToString(long timestamp, String format){
        String timeStamp = new SimpleDateFormat(format).format(new Timestamp(timestamp));
        return timeStamp;
    }

    public static int compareDateToNow(java.sql.Date date){
        java.util.Date today = new java.util.Date();
        java.util.Date compareDate = new java.util.Date(date.getTime());

        // error
        // today if after selected date
        if (today.after(compareDate) || today.equals(compareDate))
            return MovieStatusEnum.CURRENT_SHOWING.getValue();

        return MovieStatusEnum.COMING_SOON.getValue();
    }
}
