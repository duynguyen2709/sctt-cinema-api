package com.sctt.cinema.api.common.enums;

import java.util.HashMap;
import java.util.Map;

public enum CacheTypeEnum {
    BUZ_CONFIG("BuzConfig"),
    MOVIE("Movie"),
    ROOM("Room"),
    SEAT("Seat"),
    SHOWTIME("Showtime"),
    THEATER("Theater");

    private final String value;

    private static final Map<String, CacheTypeEnum> returnMap = new HashMap();

    CacheTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static CacheTypeEnum fromString(String iValue) {
        return returnMap.get(iValue);
    }

    public String toString() {
        return this.name();
    }

    static {
        CacheTypeEnum[] var0 = values();

        for (CacheTypeEnum errorCodeEnum : var0) {
            returnMap.put(errorCodeEnum.value, errorCodeEnum);
        }

    }
}
