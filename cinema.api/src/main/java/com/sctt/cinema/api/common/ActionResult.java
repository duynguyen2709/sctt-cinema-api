package com.sctt.cinema.api.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class ActionResult implements Serializable {
    public int returnCode = 0;
    public String step = "";
    public String stepResult = "";
    public String exception = "";
    public long startTime = 0L;

    public ActionResult(){
        startTime = System.currentTimeMillis();
    }
}
