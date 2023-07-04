package com.FDSC.config;

import com.FDSC.config.Intercptor.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {


//    @Bean
//    public JwtInterceptor jwtInterceptor(){
//        return new JwtInterceptor();
//    }

    @Autowired
    private JwtInterceptor jwtInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**")         //拦截所有请求判断token是否合法决定是否需要登录
                .excludePathPatterns("/user/login","/user/register","/user/getUserInfo",
                                    "/story/getStoryInfo","/story/getStoryTag","/story/slideShow","/story/latestStory","/story/hotStory","/story/usersStories","/story/usersStoriesWithFragment","/story/usersCollectStories","/story/checkCollect","/story/recommendStory",
                                    "/storyComment/loadStoryComment",
                                    "/tag/getTags",
                                    "/upload/**",
                                    "/search/**",
                                    "/fragment/loadAllFragment","/fragment/loadauthorInfoAndComment","/fragment/getFragInfo",
                                    "/announce/**");//这些接口不验证,暂时接口全部开放"

    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/");
        //映射本地文件夹
        registry.addResourceHandler("/img/**").addResourceLocations("file:D:\\image\\");
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }

}
