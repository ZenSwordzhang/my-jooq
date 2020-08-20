package com.zsx.adapter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class CustomizeHandlerInterceptorAdapter extends HandlerInterceptorAdapter {

    public CustomizeHandlerInterceptorAdapter() {
        super();
        log.info("=========CustomizeHandlerInterceptorAdapter.CustomizeHandlerInterceptorAdapter()=========");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("=========CustomizeHandlerInterceptorAdapter.preHandle()=========");
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("=========CustomizeHandlerInterceptorAdapter.postHandle()=========");
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("=========CustomizeHandlerInterceptorAdapter.afterCompletion()=========");
        super.afterCompletion(request, response, handler, ex);
    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("=========CustomizeHandlerInterceptorAdapter.afterConcurrentHandlingStarted()=========");
        super.afterConcurrentHandlingStarted(request, response, handler);
    }
}
