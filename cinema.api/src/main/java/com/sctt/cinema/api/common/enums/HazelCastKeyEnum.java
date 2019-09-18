package com.sctt.cinema.api.common.enums;

public enum HazelCastKeyEnum {

    THEATER,
    MOVIE,
    ALL;

    public static HazelCastKeyEnum getKey(String type){

        for (HazelCastKeyEnum key : HazelCastKeyEnum.values()){
            if (key.name().equalsIgnoreCase(type)) {
                return key;
            }
        }

        return null;
    }
}
