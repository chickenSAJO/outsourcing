package xyz.tomorrowlearncamp.outsourcing.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import xyz.tomorrowlearncamp.outsourcing.auth.dto.request.LoginRequestDto;
import xyz.tomorrowlearncamp.outsourcing.auth.dto.request.SignupRequestDto;
import xyz.tomorrowlearncamp.outsourcing.auth.service.AuthService;
import xyz.tomorrowlearncamp.outsourcing.domain.user.enums.Usertype;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AuthService authService;

    @Test
    public void 회원가입에_성공한다() throws Exception {
        // given
        SignupRequestDto request = new SignupRequestDto(
                "test@test.com",
                "Test1234!23",
                "01012341234",
                "testNickname",
                "testName",
                "testAddress",
                Usertype.USER.name()
        );

        given(authService.signup(any(SignupRequestDto.class))).willReturn(1L);

        // when & then
        mockMvc.perform(post("/api/v1/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    public void 로그인에_성공한다() throws Exception {
        // given
        LoginRequestDto request = new LoginRequestDto(
                "test@test.com",
                "Test1234!23"
        );

        // when & then
        mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }
}