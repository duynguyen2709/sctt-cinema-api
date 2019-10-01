package com.sctt.cinema.api.common.enums;

import java.util.HashMap;
import java.util.Map;

public enum TicketStatusEnum {
    SUCCESS(1),
    PAYING(2),
    CANCELLED(0),
    ;

    private final int value;

    private static final Map<Integer, TicketStatusEnum> returnMap = new HashMap<>();

    TicketStatusEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static TicketStatusEnum fromInt(int iValue) {
        return returnMap.get(iValue);
    }

    public String toString() {
        return this.name();
    }

    static {
        TicketStatusEnum[] var0 = values();

        for (TicketStatusEnum errorCodeEnum : var0) {
            returnMap.put(errorCodeEnum.value, errorCodeEnum);
        }

    }
}