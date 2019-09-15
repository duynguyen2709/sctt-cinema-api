package com.sctt.cinema.api.authentication;

import com.sctt.cinema.api.common.BaseResponse;
import com.sctt.cinema.api.common.enums.ReturnCodeEnum;
import com.sctt.cinema.api.util.GsonUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    private static final long serialVersionUID = -7858869558953243875L;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        final String expired = (String) request.getAttribute("expired");

        if (expired != null){
            response.getWriter().print(GsonUtils.toJsonString(new BaseResponse(ReturnCodeEnum.TOKEN_EXPIRED)));
        } else {
            response.getWriter().print(GsonUtils.toJsonString(new BaseResponse(ReturnCodeEnum.UNAUTHORIZE)));
        }

    }
}
