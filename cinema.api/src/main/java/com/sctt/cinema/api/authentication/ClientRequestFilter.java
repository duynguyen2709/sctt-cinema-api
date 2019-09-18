package com.sctt.cinema.api.authentication;

import com.sctt.cinema.api.common.BaseResponse;
import com.sctt.cinema.api.common.enums.ReturnCodeEnum;
import com.sctt.cinema.api.util.GsonUtils;
import com.sctt.cinema.api.util.HashUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Log4j2
@WebFilter(urlPatterns = "/client/public/*")
public class ClientRequestFilter implements Filter {

    @Value("${system.isDebugMode}")
    private boolean IS_DEBUG_MODE;

    private static final Long TIME_LIMIT = 30 * 1000L;

    private int    clientid;
    private long   reqdate;
    private String sig;

    private static final String HASH_KEY = "SCTT";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        ReturnCodeEnum res = getParam(servletRequest);
        if (res != ReturnCodeEnum.SUCCESS) {
            servletResponse.getWriter().print(GsonUtils.toJsonString(new BaseResponse(res)));
            return;
        }

        res = validateParam();
        if (res != ReturnCodeEnum.SUCCESS){
            servletResponse.getWriter().print(GsonUtils.toJsonString(new BaseResponse(res)));
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private ReturnCodeEnum getParam(ServletRequest servletRequest) {
        try {
            HttpServletRequest request = (HttpServletRequest) servletRequest;

            if (request.getParameter("clientid") == null ||
                    request.getParameter("clientid").isEmpty()) {
                return ReturnCodeEnum.PARAM_CLIENTID_INVALID;
            }

            if (request.getParameter("reqdate") == null ||
                    request.getParameter("reqdate").isEmpty()) {
                return ReturnCodeEnum.PARAM_REQDATE_INVALID;
            }

            if (request.getParameter("sig") == null ||
                    request.getParameter("sig").isEmpty()) {
                return ReturnCodeEnum.PARAM_SIG_INVALID;
            }

            this.clientid = Integer.parseInt(request.getParameter("clientid"));
            this.reqdate = Long.parseLong(request.getParameter("reqdate"));
            this.sig = request.getParameter("sig");

            return ReturnCodeEnum.SUCCESS;

        } catch (Exception e){
            log.error("[getParam] ex",e);
            return ReturnCodeEnum.EXCEPTION;
        }
    }

    private ReturnCodeEnum validateParam(){

        if (IS_DEBUG_MODE)
            return ReturnCodeEnum.SUCCESS;

        try {
            if (System.currentTimeMillis() - this.reqdate > TIME_LIMIT)
                return ReturnCodeEnum.TIME_LIMIT_EXCEED;

            String dataSig   = String.format("%s|%s|%s", this.clientid, this.reqdate, HASH_KEY);
            String serverSig = HashUtils.hashSHA256(dataSig);

            if (!serverSig.equalsIgnoreCase(this.sig)) {
                log.error(String.format("clientSig [%s] != serverSig [%s]", sig, serverSig));
                return ReturnCodeEnum.CHECK_SIG_NOT_MATCH;
            }

            return ReturnCodeEnum.SUCCESS;

        } catch (Exception e){
            log.error("[validateParam] ex",e);
            return ReturnCodeEnum.EXCEPTION;
        }
    }
}
