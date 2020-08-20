package com.zsx.filter;

import com.zsx.http.CustomizeHttpServletRequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@WebFilter("/*")
@Component
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
