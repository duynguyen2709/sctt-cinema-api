package com.sctt.cinema.api.common;

import com.sctt.cinema.api.common.enums.ReturnCodeEnum;
import lombok.Data;

import java.io.Serializable;

@Data
public class BaseResponse implements Serializable {

    public int returnCode;
    public String returnMessage;
    public Object data;

    public BaseResponse(ReturnCodeEnum code){
        this.returnCode = code.getValue();
        this.returnMessage = code.toString();
    }
}
