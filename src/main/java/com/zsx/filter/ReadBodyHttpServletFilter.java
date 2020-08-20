//package com.zsx.filter;
//
//import com.zsx.http.CustomizeHttpServletRequestWrapper;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Slf4j
//@WebFilter("/*")
//@Component
//public class ReadBodyHttpServletFilter extends OncePerRequestFilter {
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        log.info("=========ReadBodyHttpServletFilter.doFilterInternal()=========");
//        CustomizeHttpServletRequestWrapper requestWrapper = new CustomizeHttpServletRequestWrapper(request);
//        filterChain.doFilter(requestWrapper, response);
//    }
//
//    @Override
//    public void destroy() {
//        log.info("=========ReadBodyHttpServletFilter.destroy()=========");
//    }
//}
