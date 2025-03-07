package xyz.tomorrowlearncamp.outsourcing.global.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import xyz.tomorrowlearncamp.outsourcing.domain.user.enums.Usertype;
import xyz.tomorrowlearncamp.outsourcing.global.exception.ServerException;
import xyz.tomorrowlearncamp.outsourcing.global.util.JwtUtil;

import java.security.Key;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtUtilTest {

    @InjectMocks
    private JwtUtil jwtUtil;

    @Test
    public void 토큰이_정상적으로_생성된다() {
        // given
        Long userId = 1L;
        String mail = "test@test.com";
        Usertype type = Usertype.USER;

        jwtInit();

        // when
        String token = jwtUtil.createToken(userId, mail, type);

        // then
        assertTrue(token.startsWith("Bearer "));
    }

    @Test
    public void 토큰을_정상적으로_파싱한다() {
        // given
        String token = "Bearer testTokenString";

        jwtInit();

        // when
        String parsingToken = jwtUtil.substringToken(token);

        // then
        assertEquals("testTokenString", parsingToken);
    }

    @Test
    public void 비정상적인_토큰을_파싱한다() {
        // given
        String token = "abnormalTokenString";

        // when & then
        assertThrows(ServerException.class, () -> jwtUtil.substringToken(token));
    }

    @Test
    public void 토큰에서_클레임을_추출한다() {
        // given
        Long userId = 1L;
        String mail = "test@test.com";
        Usertype type = Usertype.USER;
        jwtInit();
        String token = jwtUtil.createToken(userId, mail, type);
        String parsedToken = jwtUtil.substringToken(token);

        // when
        Claims claims = jwtUtil.extractClaims(parsedToken);

        // then
        assertNotNull(claims);
        assertEquals("1", claims.getSubject());
        assertEquals("test@test.com", claims.get("email"));
        assertEquals(type.toString(), claims.get("usertype"));
    }

    private void jwtInit() {
        String secretKey = "7lOPWzyPwXhxly0GrFHAxKvxmzI0ed4T92jaoZH3Bp24aWnz/AZ3n/hzAacZ0Joo";
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        Key key = Keys.hmacShaKeyFor(bytes);
        jwtUtil.setKey(key);
    }

}