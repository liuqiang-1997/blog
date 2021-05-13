package com.liuqiang.blog.config;

import com.liuqiang.blog.logutils.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .addResourceLocations("classpath:/templates/");

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())  // 添加拦截器
                .addPathPatterns("admin/**")  // 添加拦截路径 /** 默认拦截所有请求（包括静态资源）
                .excludePathPatterns("/admin/**","/images/**","/css/**","/lib/**");  // 放行哪些请求路径

    }

}
