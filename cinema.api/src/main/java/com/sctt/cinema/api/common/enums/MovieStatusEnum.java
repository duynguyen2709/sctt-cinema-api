package com.sctt.cinema.api.common.enums;

import java.util.HashMap;
import java.util.Map;

public enum MovieStatusEnum {
    CURRENT_SHOWING(1),
    COMING_SOON(2),
    STOP_SHOWING(0),
    ;

    private final int value;

    private static final Map<Integer, MovieStatusEnum> returnMap = new HashMap<>();

    MovieStatusEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static MovieStatusEnum fromInt(int iValue) {
        return returnMap.get(iValue);
    }

    public String toString() {
        return this.name();
    }

    static {
        MovieStatusEnum[] var0 = values();

        for (MovieStatusEnum errorCodeEnum : var0) {
            returnMap.put(errorCodeEnum.value, errorCodeEnum);
        }

    }
}
