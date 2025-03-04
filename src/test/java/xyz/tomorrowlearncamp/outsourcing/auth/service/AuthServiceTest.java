package xyz.tomorrowlearncamp.outsourcing.auth.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import xyz.tomorrowlearncamp.outsourcing.auth.dto.request.SignupRequestDto;
import xyz.tomorrowlearncamp.outsourcing.domain.user.entity.User;
import xyz.tomorrowlearncamp.outsourcing.domain.user.enums.Usertype;
import xyz.tomorrowlearncamp.outsourcing.domain.user.repository.UserRepository;
import xyz.tomorrowlearncamp.outsourcing.global.config.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    public void 회원가입_성공() {
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
        given(passwordEncoder.encode(signupRequestDto.getPassword())).willReturn("testPassword");
        given(userRepository.save(any(User.class))).willReturn(new User());


    }

    @Test
    public void 회원가입시_중복된_이메일일_경우() {



    }

    @Test
    void login() {
    }
}