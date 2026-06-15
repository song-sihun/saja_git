package org.lion.springsecurity.beforesecirity;


import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
//@WebFilter(urlPatterns = "/filter")
public class MyFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("doFilter begin");
        chain.doFilter(request, response);
        log.info("doFilter after");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init((jakarta.servlet.FilterConfig) filterConfig);
        log.info("init");
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
        log.info("destroy");
    }
}
