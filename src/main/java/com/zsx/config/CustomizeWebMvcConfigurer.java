//package com.zsx.config;
//
//import com.zsx.adapter.CustomizeHandlerInterceptorAdapter;
//import com.zsx.filter.RequestBodyParameterReadingFilter;
//import com.zsx.filter.ReadBodyHttpServletFilter;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//import javax.servlet.Filter;
//
//@Configuration
//@Slf4j
//public class CustomizeWebMvcConfigurer implements WebMvcConfigurer {
//
//    @Bean
//    public FilterRegistrationBean<Filter> RequestBodyParameterReadingFilter() {
//        log.info("=========CustomizeWebMvcConfigurer.filterRegistrationBean()=========");
//        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
//        registration.setFilter(new RequestBodyParameterReadingFilter());
//        registration.addUrlPatterns("/*");
//        return registration;
//    }
//
//    @Bean
//    public FilterRegistrationBean<Filter> readBodyHttpServletFilter() {
//        log.info("=========CustomizeWebMvcConfigurer.filterRegistrationBean()=========");
//        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
//        registration.setFilter(new ReadBodyHttpServletFilter());
//        registration.addUrlPatterns("/*");
//        return registration;
//    }
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        log.info("=========CustomizeWebMvcConfigurer.addInterceptors()=========");
//        registry.addInterceptor(new CustomizeHandlerInterceptorAdapter()).addPathPatterns("/**");
//    }
//}