package xyz.tomorrowlearncamp.outsourcing.domain.user.service;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.tomorrowlearncamp.outsourcing.domain.user.dto.response.InfoUserResponseDto;
import xyz.tomorrowlearncamp.outsourcing.domain.user.entity.UserEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.user.enums.ErrorUserMessage;
import xyz.tomorrowlearncamp.outsourcing.domain.user.repository.UserRepository;
import xyz.tomorrowlearncamp.outsourcing.global.config.PasswordEncoder;
import xyz.tomorrowlearncamp.outsourcing.global.etc.RegexpType;
import xyz.tomorrowlearncamp.outsourcing.global.exception.InvalidRequestException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public UserEntity getUserEntity(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new InvalidRequestException(ErrorUserMessage.NOT_FOUND_USER.getErrorMassage())
        );
    }

    @Transactional(readOnly = true)
    public InfoUserResponseDto getUser(Long userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(
                () -> new InvalidRequestException(ErrorUserMessage.NOT_FOUND_USER.getErrorMassage())
        );

        return InfoUserResponseDto.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .build();
    }

    @Transactional
    public void updateUserInfo(Long userId, String nickname, String address) {
        UserEntity user = userRepository.findById(userId).orElseThrow(
                () -> new InvalidRequestException(ErrorUserMessage.NOT_FOUND_USER.getErrorMassage())
        );

        user.patchInfoUser(nickname, address);
    }

    @Transactional
    public void updateUserPassword(Long userId, String oldPassword, String newPassword) {
        UserEntity user = userRepository.findById(userId).orElseThrow(
                () -> new InvalidRequestException(ErrorUserMessage.NOT_FOUND_USER.getErrorMassage())
        );

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new InvalidRequestException(ErrorUserMessage.INVALID_PASSWORD.getErrorMassage());
        }

        String encodedPassword = passwordEncoder.encode(newPassword);

        user.patchPasswordUser(encodedPassword);
    }

    @Transactional
    public void deleteUser(Long userId, String password) {

        UserEntity user = userRepository.findById(userId).orElseThrow(
                () -> new InvalidRequestException(ErrorUserMessage.NOT_FOUND_USER.getErrorMassage())
        );

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidRequestException(ErrorUserMessage.INVALID_PASSWORD.getErrorMassage());
        }

        userRepository.delete(user);
    }
}
