package xyz.tomorrowlearncamp.outsourcing.auth.service;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.tomorrowlearncamp.outsourcing.auth.dto.request.LoginRequestDto;
import xyz.tomorrowlearncamp.outsourcing.auth.dto.request.SignupRequestDto;
import xyz.tomorrowlearncamp.outsourcing.domain.user.entity.Users;
import xyz.tomorrowlearncamp.outsourcing.domain.user.enums.Usertype;
import xyz.tomorrowlearncamp.outsourcing.domain.user.repository.UserRepository;
import xyz.tomorrowlearncamp.outsourcing.global.config.PasswordEncoder;
import xyz.tomorrowlearncamp.outsourcing.global.exception.InvalidRequestException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final HttpSession httpSession;

    @Transactional
    public Long signup(SignupRequestDto dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new InvalidRequestException("이미 존재하는 이메일입니다.");
        }

        String encodedPassword = passwordEncoder.encode(dto.getPassword());

        Usertype usertype = Usertype.of(dto.getUsertype());

        Users user = new Users(
                dto.getEmail(),
                encodedPassword,
                dto.getPhone(),
                dto.getNickname(),
                dto.getName(),
                dto.getAddress(),
                usertype
        );

        Users savedUser = userRepository.save(user);

        return savedUser.getId();
    }

    @Transactional
    public String login(@Valid LoginRequestDto dto, HttpSession session) {
        Users user = userRepository.findByEmail(dto.getEmail()).orElseThrow(()-> new InvalidRequestException("이메일 또는 비밀번호가 일치하지 않습니다."));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new InvalidRequestException("이메일 또는 비밀번호가 일치하지 않습니다.");
        }

        session.setAttribute("LOGIN_USER", user.getId());

        return "로그인 되었습니다.";
    }
}
