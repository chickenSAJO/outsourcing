package xyz.tomorrowlearncamp.outsourcing.auth.service;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
}
