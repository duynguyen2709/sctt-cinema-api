package com.sctt.cinema.api.common;

import com.sctt.cinema.api.common.enums.ReturnCodeEnum;
import lombok.Data;

import java.io.Serializable;

@Data
public class BaseResponse implements Serializable {

    public int returnCode;
    public String returnMessage;
    public Object data;

    public static BaseResponse EXCEPTION_RESPONSE = new BaseResponse(ReturnCodeEnum.EXCEPTION);

    static {
        EXCEPTION_RESPONSE.data = null;
    }

    public BaseResponse(ReturnCodeEnum code){
        this.returnCode = code.getValue();
        this.returnMessage = code.toString();
    }

    public static BaseResponse setResponse(ReturnCodeEnum code,Object data){
        BaseResponse res = new BaseResponse(code);
        res.data = data;
        return res;
    }

    public void setCode(int i) {
        this.returnCode = i;
        this.returnMessage = ReturnCodeEnum.fromInt(i).toString();
    }
}
