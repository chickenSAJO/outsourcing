package xyz.tomorrowlearncamp.outsourcing.auth.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import xyz.tomorrowlearncamp.outsourcing.domain.user.entity.UserEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.user.enums.Usertype;
import xyz.tomorrowlearncamp.outsourcing.domain.user.repository.UserRepository;
import xyz.tomorrowlearncamp.outsourcing.global.util.JwtUtil;

import java.io.IOException;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

class JwtFilterTest {

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private JwtFilter jwtFilter;

    @Mock
    private UserRepository userRepository;

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockFilterChain filterChain;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        filterChain = new MockFilterChain();
    }

    @Test
    void auth_API는_JwtFilter에서_검증하지_않는다() throws ServletException, IOException {
        // given
        request.setRequestURI("/api/v1/auth");

        // when
        jwtFilter.doFilter(request, response, filterChain);

        // then
        assertEquals(HttpServletResponse.SC_OK, response.getStatus());
        assertNotNull(filterChain.getRequest());
    }

    @Test
    void JWT_토큰이_없어서_실패한다() throws ServletException, IOException {
        // given
        request.setRequestURI("/api/v1/users");

        // when
        jwtFilter.doFilter(request, response, filterChain);

        // then
        assertEquals(HttpServletResponse.SC_UNAUTHORIZED, response.getStatus());
        assertEquals("JWT 토큰이 필요합니다.", response.getErrorMessage());
    }

    @Test
    void 유효하지_않은_JWT_서명으로_실패한다() throws ServletException, IOException {
        // given
        request.setRequestURI("/api/v1/users");
        request.addHeader("Authorization", "Bearer invalid.token.format");

        // when
        jwtFilter.doFilter(request, response, filterChain);

        // then
        assertTrue(response.getStatus() >= 400);
    }

    @Test
    void access_토큰이_만료되어_refresh_토큰을_재발행한다() throws ServletException, IOException {
        // given
        Long userId = 1L;
        String email = "test@test.com";
        Usertype usertype = Usertype.USER;
        Date date = new Date();

        String secretKey = "7lOPWzyPwXhxly0GrFHAxKvxmzI0ed4T92jaoZH3Bp24aWnz/AZ3n/hzAacZ0Joo";
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        Key key = Keys.hmacShaKeyFor(bytes);
        jwtUtil.setKey(key);

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        String expiredAccessToken = "Bearer " + (Jwts.builder()
                .setSubject(String.valueOf(userId))
                .claim("email", email)
                .claim("usertype", usertype)
                .setExpiration(new Date(date.getTime() - 1000))
                .setIssuedAt(date)
                .signWith(key, signatureAlgorithm)
                .compact());

        String refreshToken = "test-refresh-token";

        UserEntity user = new UserEntity();
        user.setId(userId);
        user.setEmail(email);
        user.setUsertype(usertype);
        user.setRefreshToken(refreshToken);

        given(userRepository.findByRefreshToken(refreshToken)).willReturn(Optional.of(user));

        String newAccessToken = "Bearer new-access-token";
        given(jwtUtil.createToken(user.getId(), user.getEmail(), user.getUsertype())).willReturn(newAccessToken);

        request.addHeader("Authorization", expiredAccessToken);

        Cookie refreshTokenCookie = new Cookie("refresh_token", refreshToken);
        request.setCookies(refreshTokenCookie);

        given(jwtUtil.substringToken(any(String.class))).willReturn("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiZW1haWwiOiJ0ZXN0QHRlc3QuY29tIiwidXNlcnR5cGUiOiJVU0VSIiwiZXhwIjoxNzQxMjM2NTM0LCJpYXQiOjE3NDEyMzY1MzV9.FOFNpKrF9CU20CD-RgGnlCAwR5tkQNuY9aUAU8xuoZw");

        Claims claims = Jwts.claims();
        claims.put("userId", userId);
        claims.put("email", email);
        claims.put("usertype", usertype);
        claims.setSubject(String.valueOf(userId));
        claims.setIssuedAt(new Date());
        claims.setExpiration(new Date(date.getTime() - 1000));

        given(jwtUtil.extractClaims(any(String.class))).willReturn(claims);

        // when
        jwtFilter.doFilter(request, response, filterChain);

        // then
        assertEquals("Bearer new-access-token", response.getHeader("Authorization"));
    }
}