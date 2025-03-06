package xyz.tomorrowlearncamp.outsourcing.auth.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;
import xyz.tomorrowlearncamp.outsourcing.domain.user.entity.UserEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.user.repository.UserRepository;
import xyz.tomorrowlearncamp.outsourcing.global.config.JwtUtil;
import xyz.tomorrowlearncamp.outsourcing.global.exception.InvalidRequestException;

import java.io.IOException;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String url = request.getRequestURI();

        if (url.startsWith("/api/v1/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        String bearerToken = request.getHeader("Authorization");

        if (bearerToken == null ) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT 토큰이 필요합니다.");
            return;
        }

        String jwt = jwtUtil.substringToken(bearerToken);

        try {
            Claims claims = jwtUtil.extractClaims(jwt);
            if (claims == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "잘못된 JWT 토큰입니다.");
                return;
            }

            if (claims.getExpiration().before(new Date())) {
                throw new ExpiredJwtException(null, claims, "JWT 토큰이 만료되었습니다.");
            }

            request.setAttribute("userId", Long.parseLong(claims.getSubject()));
            request.setAttribute("email", claims.get("email"));
            request.setAttribute("userType", claims.get("usertype"));

            // 권한에 따른 검증 코드 진행

            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token. Trying to refresh, 만료된 JWT token 입니다. 재발급을 진행합니다.", e);
            handleExpiredToken(request, response);
        } catch (SecurityException | MalformedJwtException e) {
            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.", e);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "유효하지 않는 JWT 서명입니다.");
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.", e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "지원되지 않는 JWT 토큰입니다.");
        } catch (Exception e) {
            log.error("Invalid JWT token, 유효하지 않는 JWT 토큰 입니다.", e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "유효하지 않는 JWT 토큰입니다.");
        }
    }

    private void handleExpiredToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("handleExpiredToken 실행");
        String refreshToken = getRefreshTokenFromCookie(request);

        if (refreshToken == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "리프레쉬 토큰이 없습니다.");
            return;
        }

        UserEntity user = userRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new InvalidRequestException("유효하지 않은 리프레쉬 토큰입니다."));

        String newAccessToken = jwtUtil.createToken(user.getId(), user.getEmail(), user.getUsertype());

        response.setHeader("Authorization", newAccessToken);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    private String getRefreshTokenFromCookie(HttpServletRequest request) {
        if (request.getCookies() == null) { return  null; }

        for (Cookie cookie : request.getCookies()) {
            if ("refresh_token".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
