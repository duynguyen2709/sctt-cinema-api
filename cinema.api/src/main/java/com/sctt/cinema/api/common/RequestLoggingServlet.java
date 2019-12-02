package com.sctt.cinema.api.common;

import com.sctt.cinema.api.util.GsonUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.WebUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Log4j2
public class RequestLoggingServlet extends DispatcherServlet {

    @Override
    protected void doDispatch(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        if (!(request instanceof ContentCachingRequestWrapper)) {
            request = new ContentCachingRequestWrapper(request);
        }
        if (!(response instanceof ContentCachingResponseWrapper)) {
            response = new ContentCachingResponseWrapper(response);
        }

        RequestWrapper wrapper = new RequestWrapper(request);

        try {
            super.doDispatch(wrapper, response);
        } finally {
            log(wrapper, response);
            updateResponse(response);
        }
    }

    private void log(RequestWrapper request, HttpServletResponse responseToCache) {
        try {
            LogMessage logEnt = new LogMessage();
            logEnt.method = request.getMethod();
            logEnt.path = request.getRequestURI();
            logEnt.request = UriComponentsBuilder.fromHttpRequest(new ServletServerHttpRequest(request)).build().toUriString();
            if (logEnt.method.equalsIgnoreCase("POST") ||
                    logEnt.method.equalsIgnoreCase("PUT")) {
                if (request.getBody() != null && !request.getBody().isEmpty())
                    logEnt.body = GsonUtils.fromJsonString(request.getBody(), Object.class).toString();
            } else {
                logEnt.body = null;
            }
            logEnt.response = getResponsePayload(responseToCache);

            log.info(GsonUtils.toJsonString(logEnt));
        } catch (Exception e) {
            log.error("log ex {}", e.getMessage());
        }
    }

    private MiniBaseResponse getResponsePayload(HttpServletResponse response) {
        ContentCachingResponseWrapper wrapper = WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
        if (wrapper != null) {
            byte[] buf = wrapper.getContentAsByteArray();
            if (buf.length > 0) {
                int length = Math.min(buf.length, 5120);
                try {
                    return GsonUtils.fromJsonString(new String(buf, 0, length, wrapper.getCharacterEncoding()),
                            MiniBaseResponse.class);
                } catch (Exception ex) {
                }
            }
        }
        return null;
    }

    private void updateResponse(HttpServletResponse response) throws IOException {
        ContentCachingResponseWrapper responseWrapper =
                WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
        if (responseWrapper != null) {
            responseWrapper.copyBodyToResponse();
        }
    }

    private class LogMessage implements Serializable {
        public String           method = "";
        public String           path = "";
        public String           request = "";
        public String           body = "";
        public MiniBaseResponse response = new MiniBaseResponse();
    }

    private class MiniBaseResponse implements Serializable {
        public int    returnCode = 0;
        public String returnMessage = "";
    }

    public class RequestWrapper extends HttpServletRequestWrapper {
        private final String body;

        public RequestWrapper(HttpServletRequest request) throws IOException {
            super(request);
            StringBuilder  stringBuilder  = new StringBuilder();
            BufferedReader bufferedReader = null;
            try {
                InputStream inputStream = request.getInputStream();
                if (inputStream != null) {
                    bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    char[] charBuffer = new char[128];
                    int    bytesRead  = -1;
                    while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                        stringBuilder.append(charBuffer, 0, bytesRead);
                    }
                }
                else {
                    stringBuilder.append("");
                }
            } finally {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            }
            body = stringBuilder.toString();
        }

        @Override
        public ServletInputStream getInputStream() throws IOException {
            final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body.getBytes());
            return new ServletInputStream() {
                public int read() throws IOException {
                    return byteArrayInputStream.read();
                }

                @Override public boolean isFinished() {
                    return false;
                }

                @Override public boolean isReady() {
                    return false;
                }

                @Override public void setReadListener(ReadListener listener) {
                }
            };
        }

        @Override
        public BufferedReader getReader() throws IOException {
            return new BufferedReader(new InputStreamReader(this.getInputStream()));
        }

        public String getBody() {
            return this.body;
        }
    }
}
