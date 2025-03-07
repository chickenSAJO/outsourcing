package xyz.tomorrowlearncamp.outsourcing.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import xyz.tomorrowlearncamp.outsourcing.global.config.resolver.AuthUserArgumentResolver;
import xyz.tomorrowlearncamp.outsourcing.global.interceptor.OwnerAuthInterceptor;
import xyz.tomorrowlearncamp.outsourcing.global.util.JwtUtil;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final JwtUtil jwtUtil;

    // ArgumentResolver 등록
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthUserArgumentResolver(jwtUtil));
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new OwnerAuthInterceptor())
                .addPathPatterns("/api/v1/comments**", "/api/v1/menus**", "/api/v1/stores");
    }
}
