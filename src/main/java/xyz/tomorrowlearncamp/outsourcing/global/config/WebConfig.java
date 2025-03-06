package xyz.tomorrowlearncamp.outsourcing.global.config;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import xyz.tomorrowlearncamp.outsourcing.global.interceptor.OwnerAuthInterceptor;

public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new OwnerAuthInterceptor())
                .addPathPatterns("/api/v1/comments**", "/api/v1/menus**", "/api/v1/stores");
    }
}
