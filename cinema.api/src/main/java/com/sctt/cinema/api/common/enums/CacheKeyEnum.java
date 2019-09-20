package com.sctt.cinema.api.common.enums;

public enum CacheKeyEnum {

    THEATER,
    MOVIE,
    PROVINCE_THEATER,
    USER,
    ALL;

    public static CacheKeyEnum getKey(String type){

        for (CacheKeyEnum key : CacheKeyEnum.values()){
            if (key.name().equalsIgnoreCase(type)) {
                return key;
            }
        }

        return null;
    }
}
