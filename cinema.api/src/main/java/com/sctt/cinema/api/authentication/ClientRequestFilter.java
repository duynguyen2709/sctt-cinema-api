package com.sctt.cinema.api.authentication;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.MapMaker;
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
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

@Log4j2
@WebFilter(urlPatterns = "/client/public/*")
public class ClientRequestFilter implements Filter {

    @Value("${system.isDebugMode}")
    private boolean IS_DEBUG_MODE;

    private static final Long TIME_LIMIT = 60 * 1000L;

    private static final String HASH_KEY = "SCTT";

    private static ConcurrentMap<Long, Long> REQUEST_VALIDATION_MAP;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        REQUEST_VALIDATION_MAP = CacheBuilder.newBuilder()
                                .softValues()
                                .expireAfterWrite(1,TimeUnit.MINUTES)
                                .<Long, Long>build()
                                .asMap();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        if (!IS_DEBUG_MODE) {
            ClientAuthenRequestWrapper obj = new ClientAuthenRequestWrapper();
            ReturnCodeEnum res = getParam(servletRequest, obj);
            if (res != ReturnCodeEnum.SUCCESS) {
                servletResponse.getWriter().print(GsonUtils.toJsonString(new BaseResponse(res)));
                return;
            }

            res = validateParam(obj);
            if (res != ReturnCodeEnum.SUCCESS) {
                servletResponse.getWriter().print(GsonUtils.toJsonString(new BaseResponse(res)));
                return;
            }

            REQUEST_VALIDATION_MAP.put(obj.reqdate, obj.reqdate + TIME_LIMIT);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private ReturnCodeEnum getParam(ServletRequest servletRequest, ClientAuthenRequestWrapper obj) {
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

            obj.clientid = Integer.parseInt(request.getParameter("clientid"));
            obj.reqdate = Long.parseLong(request.getParameter("reqdate"));
            obj.sig = request.getParameter("sig");

            return ReturnCodeEnum.SUCCESS;

        } catch (Exception e){
            log.error("[getParam] ex {}",e.getMessage());
            return ReturnCodeEnum.EXCEPTION;
        }
    }

    private ReturnCodeEnum validateParam(ClientAuthenRequestWrapper obj){
        try {
            if (System.currentTimeMillis() - obj.reqdate > TIME_LIMIT)
                return ReturnCodeEnum.TIME_LIMIT_EXCEED;

            if (REQUEST_VALIDATION_MAP.containsKey(obj.reqdate))
                return ReturnCodeEnum.REPLAY_ATTACK_BLOCKED;

            String dataSig   = String.format("%s|%s|%s", obj.clientid, obj.reqdate, HASH_KEY);
            String serverSig = HashUtils.hashSHA256(dataSig);

            if (!serverSig.equalsIgnoreCase(obj.sig)) {
                log.error(String.format("clientSig [%s] != serverSig [%s]", obj.sig, serverSig));
                return ReturnCodeEnum.CHECK_SIG_NOT_MATCH;
            }

            return ReturnCodeEnum.SUCCESS;

        } catch (Exception e){
            log.error("[validateParam] ex {}",e.getMessage());
            return ReturnCodeEnum.EXCEPTION;
        }
    }

    @Override
    public void destroy() {
        REQUEST_VALIDATION_MAP = null;
    }

    private class ClientAuthenRequestWrapper{
        public int    clientid;
        public long   reqdate;
        public String sig;
    }
}
