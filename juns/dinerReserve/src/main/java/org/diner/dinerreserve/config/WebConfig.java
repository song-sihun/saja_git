package org.diner.dinerreserve.config;

import org.diner.dinerreserve.config.interceptor.AdminCheckInterceptor;
import org.diner.dinerreserve.config.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1)
                .addPathPatterns("/reservation/**", "/mypage/**", "/reviews/**")
                .excludePathPatterns("/", "/users/login", "/users/logout", "/users/join", "/css/**", "/js/**", "/images/**");

        registry.addInterceptor(new AdminCheckInterceptor())
                .order(2)
                .addPathPatterns("/reservation/all")
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/css/**", "/js/**", "/images/**");
    }
}
