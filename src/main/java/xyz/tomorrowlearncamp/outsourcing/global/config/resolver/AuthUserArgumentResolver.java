package xyz.tomorrowlearncamp.outsourcing.global.config.resolver;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import xyz.tomorrowlearncamp.outsourcing.auth.annotaion.Auth;
import xyz.tomorrowlearncamp.outsourcing.auth.dto.AuthUser;
import xyz.tomorrowlearncamp.outsourcing.domain.user.enums.Usertype;
import xyz.tomorrowlearncamp.outsourcing.global.exception.AuthException;
import xyz.tomorrowlearncamp.outsourcing.global.util.JwtUtil;

@Component
@RequiredArgsConstructor
public class AuthUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtUtil jwtUtil;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasAuthAnnotation = parameter.getParameterAnnotation(Auth.class) != null;
        boolean isAuthUserType = parameter.getParameterType().equals(AuthUser.class);

        if (hasAuthAnnotation != isAuthUserType) {
            throw new AuthException("@Auth와 AuthUser 타입은 함께 사용되어야 합니다.");
        }

        return hasAuthAnnotation;
    }

    @Override
    public Object resolveArgument(
            @Nullable MethodParameter parameter,
            @Nullable ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            @Nullable WebDataBinderFactory binderFactory
    ) {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new AuthException("Authorization 헤더가 없거나 올바르지 않습니다.");
        }

        String token = authorizationHeader.substring(7);
        Claims claims = jwtUtil.extractClaims(token);

        Long userId = Long.parseLong(claims.getSubject());
        String email = claims.get("email", String.class);
        Usertype userType = Usertype.valueOf(claims.get("usertype", String.class));

        return new AuthUser(userId, email, userType);
    }
}
