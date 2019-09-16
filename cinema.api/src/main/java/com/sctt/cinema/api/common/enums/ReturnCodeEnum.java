package com.sctt.cinema.api.common.enums;

import java.util.HashMap;

public enum ReturnCodeEnum {

    UNAUTHORIZE(-401),
    TOKEN_EXPIRED(-400),
    WRONG_USERNAME_OR_PASSWORD(-1),
    SUCCESS(1),
    EXCEPTION(0);

    private final int value;

    private static final HashMap<Integer, ReturnCodeEnum> returnMap = new HashMap<>();

    ReturnCodeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static ReturnCodeEnum fromInt(int iValue) {
        return returnMap.get(iValue);
    }

    public String toString() {
        return this.name();
    }

    static {
        ReturnCodeEnum[] var0 = values();

        for (ReturnCodeEnum errorCodeEnum : var0) {
            returnMap.put(errorCodeEnum.value, errorCodeEnum);
        }

    }
}
