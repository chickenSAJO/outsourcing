package xyz.tomorrowlearncamp.outsourcing.auth.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import xyz.tomorrowlearncamp.outsourcing.auth.AuthValidationMessages;
import xyz.tomorrowlearncamp.outsourcing.auth.dto.request.LoginRequestDto;
import xyz.tomorrowlearncamp.outsourcing.auth.dto.request.SignupRequestDto;
import xyz.tomorrowlearncamp.outsourcing.domain.user.entity.UserEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.user.enums.Usertype;
import xyz.tomorrowlearncamp.outsourcing.domain.user.repository.UserRepository;
import xyz.tomorrowlearncamp.outsourcing.global.config.JwtUtil;
import xyz.tomorrowlearncamp.outsourcing.global.config.PasswordEncoder;
import xyz.tomorrowlearncamp.outsourcing.global.exception.InvalidRequestException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Validated
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public Long signup(SignupRequestDto dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new InvalidRequestException(AuthValidationMessages.EMAIL_DUPLICATION_MESSAGE);
        }

        String encodedPassword = passwordEncoder.encode(dto.getPassword());

        Usertype usertype = Usertype.of(dto.getUsertype());

        UserEntity user = new UserEntity(
                dto.getEmail(),
                encodedPassword,
                dto.getPhone(),
                dto.getNickname(),
                dto.getName(),
                dto.getAddress(),
                usertype
        );

        UserEntity savedUser = userRepository.save(user);

        return savedUser.getId();
    }

    @Transactional
    public String login(@Valid LoginRequestDto dto, HttpServletResponse response) {
        UserEntity user = userRepository.findByEmail(dto.getEmail()).orElseThrow(()-> new InvalidRequestException(AuthValidationMessages.EMAIL_OR_PASSWORD_DIFF_MESSAGE));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new InvalidRequestException(AuthValidationMessages.EMAIL_OR_PASSWORD_DIFF_MESSAGE);
        }

        String accessToken = jwtUtil.createToken(user.getId(), user.getEmail(), user.getUsertype());
        String refreshToken = UUID.randomUUID().toString();

        user.setRefreshToken(refreshToken);
        userRepository.save(user);

        ResponseCookie refreshCookie = ResponseCookie.from("refresh_token", refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(7 * 24 * 60 * 60)
                .sameSite("Strict")
                .build();

        response.addHeader("Set-Cookie", refreshCookie.toString());

        return "access token : " + accessToken;
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) {
        Long userId = Long.valueOf(request.getAttribute("userId").toString());
        deleteRefreshToken(userId);
        removeRefreshTokenCookie(response);
    }

    private void deleteRefreshToken(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다.(ID : " + userId + ")"));

        user.setRefreshToken(null);
        userRepository.save(user);
    }

    private void removeRefreshTokenCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("refresh_token", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
