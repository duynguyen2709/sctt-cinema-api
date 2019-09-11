package com.sctt.cinema.api.common.enums;

import java.util.HashMap;

public enum ReturnCodeEnum {

    SUCCESSFUL(1),
    EXCEPTION(0);

    private final int value;

    private static final HashMap<Integer, ReturnCodeEnum> returnMap = new HashMap();

    private ReturnCodeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static ReturnCodeEnum fromInt(int iValue) {
        return (ReturnCodeEnum)returnMap.get(iValue);
    }

    public static ReturnCodeEnum fromIntDefault(int iValue, ReturnCodeEnum defaultValue) {
        ReturnCodeEnum code = (ReturnCodeEnum)returnMap.get(iValue);
        return code != null ? code : defaultValue;
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
