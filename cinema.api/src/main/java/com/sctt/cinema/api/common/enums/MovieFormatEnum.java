package com.sctt.cinema.api.common.enums;

import java.util.HashMap;
import java.util.Map;

public enum MovieFormatEnum {
    _2D(1,"2D"),
    _3D(2,"3D"),
    ;

    private final int value;
    private final String name;

    private static final Map<Integer, MovieFormatEnum> returnMap = new HashMap<>();

    MovieFormatEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public int getValue() {
        return this.value;
    }

    public static MovieFormatEnum fromInt(int iValue) {
        return returnMap.get(iValue);
    }

    public String toString() {
        return this.name();
    }

    static {
        MovieFormatEnum[] var0 = values();

        for (MovieFormatEnum errorCodeEnum : var0) {
            returnMap.put(errorCodeEnum.value, errorCodeEnum);
        }

    }
}
