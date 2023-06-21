package com.FDSC.config;

import com.FDSC.config.Intercptor.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Bean
    public JwtInterceptor jwtInterceptor(){
        return new JwtInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor())
                .addPathPatterns("/**")         //拦截所有请求判断token是否合法决定是否需要登录
                .excludePathPatterns("/user/login","/user/register","/water/order/export","/**/export");//这些接口不验证,导入不放开,导出放开"/**/export","/**/import"

    }
}
