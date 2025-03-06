package xyz.tomorrowlearncamp.outsourcing.auth.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletResponse;
import xyz.tomorrowlearncamp.outsourcing.auth.dto.request.LoginRequestDto;
import xyz.tomorrowlearncamp.outsourcing.auth.dto.request.SignupRequestDto;
import xyz.tomorrowlearncamp.outsourcing.domain.user.entity.UserEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.user.enums.Usertype;
import xyz.tomorrowlearncamp.outsourcing.domain.user.repository.UserRepository;
import xyz.tomorrowlearncamp.outsourcing.global.config.JwtUtil;
import xyz.tomorrowlearncamp.outsourcing.global.config.PasswordEncoder;
import xyz.tomorrowlearncamp.outsourcing.global.exception.InvalidRequestException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthService authService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpServletRequest request;

    @Test
    public void 회원가입에_성공한다() {
        // given
        SignupRequestDto signupRequestDto = new SignupRequestDto(
                "test@test.com",
                "Test1234!23",
                "01012341234",
                "testNickname",
                "testName",
                "testAddress",
                Usertype.USER.name()
        );

        given(userRepository.existsByEmail(signupRequestDto.getEmail())).willReturn(false);
        given(passwordEncoder.encode(signupRequestDto.getPassword())).willReturn("encodedPassword");

        UserEntity savedUser = makeUserEntity();
        savedUser.setId(1L);

        given(userRepository.save(any(UserEntity.class))).willReturn(savedUser);

        // when
        Long userId = authService.signup(signupRequestDto);

        // when & then
        assertEquals(1L, userId);
    }

    @Test
    public void 회원가입시_중복된_이메일일_경우() {
        // given
        SignupRequestDto signupRequestDto = new SignupRequestDto(
                "test@test.com",
                "Test1234!23",
                "01012341234",
                "testNickname",
                "testName",
                "testAddress",
                Usertype.USER.name()
        );

        given(userRepository.existsByEmail(signupRequestDto.getEmail())).willReturn(true);

        // when & then
        assertThrows(InvalidRequestException.class, () -> authService.signup(signupRequestDto));
    }

    @Test
    public void 로그인에_성공한다() {
        // given
        LoginRequestDto loginRequestDto = new LoginRequestDto(
                "test@test.com",
                "Test1234!23"
        );

        UserEntity savedUser = makeUserEntity();

        given(userRepository.findByEmail(loginRequestDto.getEmail())).willReturn(Optional.of(savedUser));
        given(passwordEncoder.matches(loginRequestDto.getPassword(), savedUser.getPassword())).willReturn(true);

        given(jwtUtil.createToken(savedUser.getId(), savedUser.getEmail(), savedUser.getUsertype())).willReturn("accessToken");
        savedUser.setRefreshToken("refreshToken");

        response = new MockHttpServletResponse();

        // when
        String accessToken = authService.login(loginRequestDto, response);

        // then
        assertEquals("access token : accessToken", accessToken);
        String setCookieHeader = response.getHeader("Set-Cookie");
        assertNotNull(setCookieHeader);
    }

    @Test
    public void 로그인시_존재하지_않는_이메일일_경우() {
        // given
        LoginRequestDto loginRequestDto = new LoginRequestDto(
                "diff@test.com",
                "Test1234!23"
        );

        given(userRepository.findByEmail(loginRequestDto.getEmail())).willReturn(Optional.empty());

        // when & then
        assertThrows(InvalidRequestException.class, () -> authService.login(loginRequestDto, response));
    }

    @Test
    public void 로그인시_비밀번호가_일치하지_않는_경우() {
        // given
        UserEntity userEntity = makeUserEntity();

        LoginRequestDto loginRequestDto = new LoginRequestDto(
                "test@test.com",
                "Diff1234!23"
        );

        given(userRepository.findByEmail(loginRequestDto.getEmail())).willReturn(Optional.of(userEntity));
        given(passwordEncoder.matches(loginRequestDto.getPassword(), userEntity.getPassword())).willReturn(false);

        // when & then
        assertThrows(InvalidRequestException.class, () -> authService.login(loginRequestDto, response));
    }

    private UserEntity makeUserEntity() {
        return new UserEntity(
                "test@test.com",
                "Test1234!23",
                "01012341234",
                "testNickname",
                "testName",
                "testAddress",
                Usertype.USER
        );
    }

    @Test
    public void 로그아웃에_성공한다() {
        // given
        Long userId = 1L;
        UserEntity userEntity = makeUserEntity();
        userEntity.setId(userId);
        userEntity.setRefreshToken("refreshToken");

        given(request.getAttribute("userId")).willReturn(userId);
        given(userRepository.findById(userId)).willReturn(Optional.of(userEntity));

        // when
        authService.logout(request, response);

        // then
        assertNull(userEntity.getRefreshToken());
        verify(userRepository).save(userEntity);

    }
}