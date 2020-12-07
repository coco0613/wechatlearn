package cn.leslie.weChatLearn.config;

import cn.leslie.weChatLearn.interceoter.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class LoginInterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //拦截路径
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("user/api/v1/*/**");
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
