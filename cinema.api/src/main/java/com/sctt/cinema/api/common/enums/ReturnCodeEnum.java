package com.sctt.cinema.api.common.enums;

import java.util.HashMap;

public enum ReturnCodeEnum {

    INIT(2),
    SUCCESS(1),
    EXCEPTION(0),
    UNAUTHORIZE(-401),
    TOKEN_EXPIRED(-400),
    WRONG_USERNAME_OR_PASSWORD(-1),
    ACCOUNT_LOCKED(-2),

    PARAM_CLIENTID_INVALID(-3),
    PARAM_REQDATE_INVALID(-4),
    PARAM_SIG_INVALID(-5),
    PARAM_DATA_INVALID(-6),

    CHECK_SIG_NOT_MATCH(-7),
    TIME_LIMIT_EXCEED(-8),
    REPLAY_ATTACK_BLOCKED(-9),

    USER_NOT_FOUND(-10),
    SHOWTIME_NOT_FOUND(-11),
    THEATER_NOT_FOUND(-12),
    MOVIE_NOT_FOUND(-13),
    ROOM_NOT_FOUND(-14),
    BUZ_CONFIG_NOT_FOUND(-15),
    TICKET_NOT_FOUND(-16),
    BOOKED_SEAT_NOT_FOUND(-17),
    SEAT_NOT_FOUND(-18),

    SEAT_NOT_EMPTY(-19),
    DATA_NOT_VALID(-20),

    PARAM_TYPE_INVALID(-21),
    PARAM_ID_INVALID(-22),
    PARAM_DATE_INVALID(-23),

    TICKET_CANCELLED(-24),
    TICKET_PAID(-25)
    ;


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
