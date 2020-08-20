## 异常

* 注：包含 **@RestControllerAdvice**注解的类，就一个生效，**@Order(Ordered.HIGHEST_PRECEDENCE)** 优先级高的执行

### 异常优先级

#### 请求路径存在时
* ExceptionHandler > DefaultErrorAttributes

#### 请求路径不存在时
* AbstractErrorController > DefaultErrorAttributes > ExceptionHandler
    * DefaultErrorAttributes
        * getErrorAttributes() > getError()


### 请求顺序
* http://localhost:8088/exception/user
```
{
    "name": "non_exist",
    "age": 18
}
```

#### 顺序1 
* 配置 **LogFilter**和 **CustomizeWebMvcConfigurer**
* 1.> =========LogFilter.doFilter()=========
* 2.> =========CustomizeHttpServletRequestWrapper.CustomizeHttpServletRequestWrapper()=========
* 3.> =========CustomizeHandlerInterceptorAdapter.preHandle()=========
* 4.> =========CustomizeHttpServletRequestWrapper.getInputStream()=========
* 5.> =========NonExistingUserException.NonExistingUserException()=========
* 6.> =========GlobalExceptionHandler.nonExistingUserException()=========
* 7.> =========CustomizeHandlerInterceptorAdapter.afterCompletion()=========

#### 顺序2
* 配置 **ReadBodyHttpServletFilter**和 **CustomizeWebMvcConfigurer**
* 1.> =========ReadBodyHttpServletFilter.doFilterInternal()=========
* 2.> =========CustomizeHttpServletRequestWrapper.CustomizeHttpServletRequestWrapper()=========
* 3.> =========CustomizeHandlerInterceptorAdapter.preHandle()=========
* 4.> =========CustomizeHttpServletRequestWrapper.getInputStream()=========
* 5.> =========NonExistingUserException.NonExistingUserException()=========
* 6.> =========GlobalExceptionHandler.nonExistingUserException()=========
* 7.> =========CustomizeHttpServletRequestWrapper.CustomizeHttpServletRequestWrapper())=========
* 8.> =========CustomizeHttpServletRequestWrapper.getInputStream()=========
* 9.> =========CustomizeHttpServletRequestWrapper.getReader()=========
* 10.> =========CustomizeHttpServletRequestWrapper.getInputStream()=========
* 11.> =========CustomizeHandlerInterceptorAdapter.afterCompletion()=========

#### 顺序3 配置 **LogFilter**、**ReadBodyHttpServletFilter**和 **CustomizeWebMvcConfigurer**
* 1.> =========LogFilter.doFilter()=========
* 2.> =========CustomizeHttpServletRequestWrapper.CustomizeHttpServletRequestWrapper()=========
* 3.> =========ReadBodyHttpServletFilter.doFilterInternal()=========
* 4.> =========CustomizeHttpServletRequestWrapper.CustomizeHttpServletRequestWrapper()=========
* 5.> =========CustomizeHttpServletRequestWrapper.getInputStream()=========
* 6.> =========CustomizeHandlerInterceptorAdapter.preHandle()=========
* 7.> =========CustomizeHttpServletRequestWrapper.getInputStream()=========
* 8.> =========NonExistingUserException.NonExistingUserException()=========
* 9.> =========GlobalExceptionHandler.nonExistingUserException()=========
* 10.> =========CustomizeHttpServletRequestWrapper.CustomizeHttpServletRequestWrapper()=========
* 11.> =========CustomizeHttpServletRequestWrapper.getInputStream()=========
* 12.> =========CustomizeHttpServletRequestWrapper.getReader()=========
* 13.> =========CustomizeHttpServletRequestWrapper.getInputStream()=========
* 14.> =========CustomizeHandlerInterceptorAdapter.afterCompletion()=========

### 启动加载顺序
* 1.> =========CustomizeWebMvcConfigurer.filterRegistrationBean()=========
* 2.> =========LogFilter.init()=========
* 3.> =========WebErrorConfiguration.errorAttributes()=========
* 4.> =========CustomizeErrorController.CustomizeErrorController()=========
* 5.> =========CustomizeWebMvcConfigurer.addInterceptors()=========
* 6.> =========CustomizeHandlerInterceptorAdapter.CustomizeHandlerInterceptorAdapter()=========

* 1.>
* 2.>
* 3.>
* 4.>
* 5.>
* 6.>
* 7.>
* 8.>
* 9.>
* 10.>
* 11.>
* 12.>
* 13.>
* 14.>
* 15.>

## code

### 过滤器

* 1.实现Filter接口
```java
package com.zsx.filter;

import com.zsx.http.CustomizeHttpServletRequestWrapper;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
public class LogFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("=========LogFilter.init()=========");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("=========LogFilter.doFilter()=========");
        ServletRequest req = null;
        if(request instanceof HttpServletRequest) {
            req = new CustomizeHttpServletRequestWrapper((HttpServletRequest) request);
        }
        if(req == null) {
            chain.doFilter(request, response);
        } else {
            chain.doFilter(req, response);
        }
    }

    @Override
    public void destroy() {
        log.info("=========LogFilter.destroy()=========");
    }
}
```
```java
package com.zsx.config;

import com.zsx.adapter.CustomizeHandlerInterceptorAdapter;
import com.zsx.filter.LogFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;

@Configuration
@Slf4j
public class CustomizeWebMvcConfigurer implements WebMvcConfigurer {

    @Bean
    public FilterRegistrationBean<Filter> logFilter() {
        log.info("=========CustomizeWebMvcConfigurer.filterRegistrationBean()=========");
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new LogFilter());
        registration.addUrlPatterns("/*");
        return registration;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("=========CustomizeWebMvcConfigurer.addInterceptors()=========");
        registry.addInterceptor(new CustomizeHandlerInterceptorAdapter()).addPathPatterns("/**");
    }
}
```
* 2.继承OncePerRequestFilter类
```java
package com.zsx.filter;

import com.zsx.http.CustomizeHttpServletRequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@WebFilter("/*")
public class ReadBodyHttpServletFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("=========ReadBodyHttpServletFilter.doFilterInternal()=========");
        CustomizeHttpServletRequestWrapper requestWrapper = new CustomizeHttpServletRequestWrapper(request);
        filterChain.doFilter(requestWrapper, response);
    }

    @Override
    public void destroy() {
        log.info("=========ReadBodyHttpServletFilter.destroy()=========");
    }
}

```
```java
package com.zsx.config;

import com.zsx.adapter.CustomizeHandlerInterceptorAdapter;
import com.zsx.filter.ReadBodyHttpServletFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;

@Configuration
@Slf4j
public class CustomizeWebMvcConfigurer implements WebMvcConfigurer {

    @Bean
    public FilterRegistrationBean<Filter> readBodyHttpServletFilter() {
        log.info("=========CustomizeWebMvcConfigurer.filterRegistrationBean()=========");
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new ReadBodyHttpServletFilter());
        registration.addUrlPatterns("/*");
        return registration;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("=========CustomizeWebMvcConfigurer.addInterceptors()=========");
        registry.addInterceptor(new CustomizeHandlerInterceptorAdapter()).addPathPatterns("/**");
    }
}
```

* 3.分别实现Filter接口、继承OncePerRequestFilter类
```java
package com.zsx.config;

import com.zsx.adapter.CustomizeHandlerInterceptorAdapter;
import com.zsx.filter.LogFilter;
import com.zsx.filter.ReadBodyHttpServletFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;

@Configuration
@Slf4j
public class CustomizeWebMvcConfigurer implements WebMvcConfigurer {

    @Bean
    public FilterRegistrationBean<Filter> logFilter() {
        log.info("=========CustomizeWebMvcConfigurer.filterRegistrationBean()=========");
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new LogFilter());
        registration.addUrlPatterns("/*");
        return registration;
    }

    @Bean
    public FilterRegistrationBean<Filter> readBodyHttpServletFilter() {
        log.info("=========CustomizeWebMvcConfigurer.filterRegistrationBean()=========");
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new ReadBodyHttpServletFilter());
        registration.addUrlPatterns("/*");
        return registration;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("=========CustomizeWebMvcConfigurer.addInterceptors()=========");
        registry.addInterceptor(new CustomizeHandlerInterceptorAdapter()).addPathPatterns("/**");
    }
}

```

* 4.不自定义拦截器，通过注解 @WebFilter("/*")、@Component 实现
```java
package com.zsx.filter;

import com.zsx.http.CustomizeHttpServletRequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@WebFilter("/*")
@Component
public class ReadBodyHttpServletFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("=========ReadBodyHttpServletFilter.doFilterInternal()=========");
        CustomizeHttpServletRequestWrapper requestWrapper = new CustomizeHttpServletRequestWrapper(request);
        filterChain.doFilter(requestWrapper, response);
    }

    @Override
    public void destroy() {
        log.info("=========ReadBodyHttpServletFilter.destroy()=========");
    }
}

```




## 参考链接

### 自定义异常
* [Exception](https://thepracticaldeveloper.com/2019/09/09/custom-error-handling-rest-controllers-spring-boot/)

