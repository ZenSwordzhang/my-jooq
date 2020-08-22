package com.zsx.error;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@Log4j2
public class CustomizeErrorAttributes extends DefaultErrorAttributes {

    /**
     * 在controller发生异常，且异常未捕获时执行
     * @param webRequest
     * @return
     */
    @Override
    public Throwable getError(WebRequest webRequest) {
        log.info("=========CustomizeErrorAttributes.getError()=========");
        Throwable exception = super.getError(webRequest);
        if (exception != null) {
            log.error("==========CustomizeErrorAttributes.getError()============");
        }
        return exception;
    }

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
        log.info("=========CustomizeErrorAttributes.getErrorAttributes()=========");
        return super.getErrorAttributes(webRequest, options);
    }

}
