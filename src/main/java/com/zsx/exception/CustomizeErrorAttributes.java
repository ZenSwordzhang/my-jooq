package com.zsx.exception;

import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

@Component
public class CustomizeErrorAttributes extends DefaultErrorAttributes {

    /**
     * 在controller发生异常，且异常未捕获时执行
     * @param webRequest
     * @return
     */
    @Override
    public Throwable getError(WebRequest webRequest) {
        Throwable exception = super.getError(webRequest);
        if (exception != null) {
            System.out.println("==========CustomizeErrorAttributes============");
        }
        return exception;
    }

}
