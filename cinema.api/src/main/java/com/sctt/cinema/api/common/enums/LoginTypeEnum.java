package com.sctt.cinema.api.common.enums;

import java.util.HashMap;
import java.util.Map;

public enum LoginTypeEnum {
    NORMAL(1),
    _3RD_PARTY(2),
    ;

    private final int value;

    private static final Map<Integer, LoginTypeEnum> returnMap = new HashMap<>();

    LoginTypeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static LoginTypeEnum fromtInt(int iValue) {
        return returnMap.get(iValue);
    }

    public String toString() {
        return this.name();
    }

    static {
        LoginTypeEnum[] var0 = values();

        for (LoginTypeEnum errorCodeEnum : var0) {
            returnMap.put(errorCodeEnum.value, errorCodeEnum);
        }

    }
}
