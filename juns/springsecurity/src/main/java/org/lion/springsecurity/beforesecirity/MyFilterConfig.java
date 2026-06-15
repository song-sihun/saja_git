package org.lion.springsecurity.beforesecirity;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyFilterConfig {
//    @Bean
//    public FilterRegistrationBean<MyFilter> filterRegistrationBean(){
//        FilterRegistrationBean<MyFilter> registrationBean = new FilterRegistrationBean<>();
//        registrationBean.setFilter(new MyFilter());
//        registrationBean.addUrlPatterns("/filter");
//        registrationBean.setOrder(1);
//        return registrationBean;
//    }
//
//    @Bean
//    public FilterRegistrationBean<FilterExam> filterExamFilterRegistrationBean(){
//        FilterRegistrationBean<FilterExam> registrationBean = new FilterRegistrationBean<>();
//        registrationBean.setFilter(new FilterExam());
//        registrationBean.addUrlPatterns("/*");
//        registrationBean.setOrder(2);
//        return registrationBean;
//    }

    @Bean
    public FilterRegistrationBean<UserFilter> userFilterRegistrationBean(){
        FilterRegistrationBean<UserFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new UserFilter());
        registrationBean.addUrlPatterns("/users/*");
        registrationBean.setOrder(3);
        return registrationBean;
    }

}
