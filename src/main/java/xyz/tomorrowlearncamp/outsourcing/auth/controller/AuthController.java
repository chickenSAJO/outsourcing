package xyz.tomorrowlearncamp.outsourcing.auth.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import xyz.tomorrowlearncamp.outsourcing.auth.annotaion.Auth;
import xyz.tomorrowlearncamp.outsourcing.auth.dto.AuthUser;
import xyz.tomorrowlearncamp.outsourcing.auth.dto.request.LoginRequestDto;
import xyz.tomorrowlearncamp.outsourcing.auth.dto.request.SignupRequestDto;
import xyz.tomorrowlearncamp.outsourcing.auth.service.AuthService;
import xyz.tomorrowlearncamp.outsourcing.domain.user.entity.UserEntity;

import java.net.URI;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    public final AuthService authService;

    @PostMapping("/v1/auth/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody SignupRequestDto dto) {
        Long userId = authService.signup(dto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{userId}")
                .buildAndExpand(userId)
                .toUri();

        return ResponseEntity.created(location).body("");
    }

    @PostMapping("/v1/auth/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequestDto dto, HttpServletResponse response) {
        return ResponseEntity.ok(authService.login(dto, response));
    }

    @PostMapping("/v1/auth/logout")
    public ResponseEntity<String> logout(HttpServletResponse response, @Auth AuthUser user) {
        authService.logout(response, user.getId());
        return ResponseEntity.ok().build();
    }

}
