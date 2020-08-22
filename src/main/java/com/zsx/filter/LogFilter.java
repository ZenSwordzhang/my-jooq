package com.zsx.filter;

import com.zsx.http.CustomizeHttpServletRequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@Configuration
public class LogFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("=========LogFilter.init()=========");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("=========LogFilter.doFilter()=========");
        if (request instanceof HttpServletRequest) {
            String contentType = request.getContentType();
            // 如果处理上传文件数据，下面方法执行到chain.doFilter()时会出线异常，所以此处只处理@RequestBody数据
            if (!StringUtils.isEmpty(contentType) && contentType.contains(MediaType.APPLICATION_JSON_VALUE)) {
                log.info("=========LogFilter.doFilter().jsonRequest=========");
                ServletRequest jsonRequest = new CustomizeHttpServletRequestWrapper((HttpServletRequest) request);
                chain.doFilter(jsonRequest, response);
                return;
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        log.info("=========LogFilter.destroy()=========");
    }
}
