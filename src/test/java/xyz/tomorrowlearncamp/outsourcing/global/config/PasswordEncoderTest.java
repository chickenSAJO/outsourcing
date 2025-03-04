package xyz.tomorrowlearncamp.outsourcing.global.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class PasswordEncoderTest {

    @InjectMocks
    private PasswordEncoder encoder;

    @Test
    void matches() {
        // given
        String testPassword = "password";
        String encodedPassword = encoder.encode(testPassword);

        // when
        boolean matches = encoder.matches(testPassword, encodedPassword);

        // then
        assertTrue(matches);
    }
}