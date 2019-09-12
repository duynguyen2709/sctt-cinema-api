package com.sctt.cinema.api.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.log4j.Log4j2;

import java.lang.reflect.Type;

@Log4j2
public class GsonUtils {

    private static final Gson gson;

    public GsonUtils() {
    }

    public static String toJsonString(Object obj) {
        return gson.toJson(obj);
    }

    public static <T> T fromJsonString(String sJson, Class<T> t) {
        return gson.fromJson(sJson, t);
    }

    public static <T> T fromJsonStringDefaultNull(String sJson, Class<T> t) {
        try {
            return gson.fromJson(sJson, t);
        } catch (Exception var3) {
            log.error(String.format("[fromJsonStringDefaultNull] parse %s to %s ex ", sJson, t.getName()), var3);
            return null;
        }
    }

    public static <T> T json2Collection(String sJson, Type t) {
        return gson.fromJson(sJson, t);
    }

    static {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        gson = gsonBuilder.disableHtmlEscaping().create();
    }
}
