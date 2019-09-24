package com.sctt.cinema.api.util;

public class BuzUtils {

    public static long generateID(long currentID) {
        // id format: YYMMDDxxxx
        // YYMMDD: current date
        // xxxx auto increase
        long   id        = 0;
        String oldYYMMDD = "";

        if (currentID != 0) {
            oldYYMMDD = String.valueOf(currentID).substring(0, 6);
        }

        String now = DateTimeUtils.getCurrentYYMMDD();

        if (!oldYYMMDD.equalsIgnoreCase(now)) {
            id = Long.parseLong(now + "0001");
        } else {
            id = currentID + 1;
        }

        return id;
    }
}
