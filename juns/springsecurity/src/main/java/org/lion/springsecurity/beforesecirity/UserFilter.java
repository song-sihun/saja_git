package org.lion.springsecurity.beforesecirity;

import jakarta.servlet.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class UserFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {

            log.info("Before Thread name : {}", Thread.currentThread().getName());

            User user = new User();
            user.setName("test");
            UserContext.setUser(user);

            chain.doFilter(request, response);

            UserContext.setUser(null);
            UserContext.removeUser();

        } finally {
            UserContext.setUser(null);
        }

        log.info("After Thread name : {}", Thread.currentThread().getName());
    }

}
