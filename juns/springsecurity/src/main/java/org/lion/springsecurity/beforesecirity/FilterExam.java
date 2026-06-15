package org.lion.springsecurity.beforesecirity;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
//@WebFilter(urlPatterns = "/*")
public class FilterExam implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("FilterExam init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("FilterExam doFilter begin");
        chain.doFilter(request, response);
        log.info("FilterExam doFilter after");
    }

    @Override
    public void destroy() {
        log.info("FilterExam destroy");
    }

}
