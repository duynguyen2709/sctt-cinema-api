package com.sctt.cinema.api.common.enums;

import java.util.HashMap;
import java.util.Map;

public enum RoleEnum {
    ADMIN(0),
    STAFF(1),
    CUSTOMER(2),
    ;

    private final int value;

    private static final Map<Integer, RoleEnum> returnMap = new HashMap<>();

    RoleEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static RoleEnum fromInt(int iValue) {
        return returnMap.get(iValue);
    }

    public String toString() {
        return this.name();
    }

    static {
        RoleEnum[] var0 = values();

        for (RoleEnum errorCodeEnum : var0) {
            returnMap.put(errorCodeEnum.value, errorCodeEnum);
        }

    }
}
