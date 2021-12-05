package com.aseproject.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @classname InterceptorConfigurer
 * @description Register all interceptors
 * @author Yicheng Lu
 * @date Dec 5th, 2021
 */
@Configuration
public class InterceptorConfigurer implements WebMvcConfigurer {

    @Override
    public void addInterceptors (InterceptorRegistry registry) {
        org.springframework.web.servlet.config.annotation.WebMvcConfigurer.super.addInterceptors(registry);
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/upload");
        registry.addInterceptor(new SessionInterceptor()).addPathPatterns("/**");
    }

}

