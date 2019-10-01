package com.sctt.cinema.api.common.enums;

import java.util.HashMap;
import java.util.Map;

public enum ShowtimeTypeEnum {
    MOVIE(1),
    THEATER(2),
    ;

    private final int value;

    private static final Map<Integer, ShowtimeTypeEnum> returnMap = new HashMap<>();

    ShowtimeTypeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static ShowtimeTypeEnum fromtInt(int iValue) {
        return returnMap.get(iValue);
    }

    public String toString() {
        return this.name();
    }

    static {
        ShowtimeTypeEnum[] var0 = values();

        for (ShowtimeTypeEnum errorCodeEnum : var0) {
            returnMap.put(errorCodeEnum.value, errorCodeEnum);
        }

    }
}
