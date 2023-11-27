package com.itheima.mp.config;

import com.itheima.mp.filter.AuthHandlerInterceptor;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AuthWebMvcConfigurer implements WebMvcConfigurer {
    @Autowired
    AuthHandlerInterceptor authHandlerInterceptor;

    @Override
    public void addInterceptors (InterceptorRegistry registry) {
        registry.addInterceptor(authHandlerInterceptor)
                .addPathPatterns("/*")
                .excludePathPatterns("/auth/login")
                .excludePathPatterns("/create");
    }
}
