package com.sctt.cinema.api.common.enums;

import java.util.HashMap;

public enum ReturnCodeEnum {

    SUCCESS(1),
    EXCEPTION(0);

    private final int value;

    private static final HashMap<Integer, ReturnCodeEnum> returnMap = new HashMap();

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
        int              var1 = var0.length;

        for(int var2 = 0; var2 < var1; ++var2) {
            ReturnCodeEnum errorCodeEnum = var0[var2];
            returnMap.put(errorCodeEnum.value, errorCodeEnum);
        }

    }
}
