package com.example.sms.cmm.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.example.sms.user.web.LoginInterceptor;

/**
 * 웹 MVC 설정 (예전 dispatcher-servlet.xml 의 interceptor 설정을 옮김)
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/user/login.do", "/user/actionLogin.do",
                        "/css/**", "/js/**", "/images/**", "/error");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", "/report/dashboard.do");
    }
}
