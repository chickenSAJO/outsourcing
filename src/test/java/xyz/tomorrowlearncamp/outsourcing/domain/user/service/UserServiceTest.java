package xyz.tomorrowlearncamp.outsourcing.domain.user.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.stereotype.Repository;
import org.springframework.test.util.ReflectionTestUtils;
import xyz.tomorrowlearncamp.outsourcing.domain.user.dto.response.InfoUserResponseDto;
import xyz.tomorrowlearncamp.outsourcing.domain.user.entity.UserEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.user.enums.ErrorUserMessage;
import xyz.tomorrowlearncamp.outsourcing.domain.user.repository.UserRepository;
import xyz.tomorrowlearncamp.outsourcing.global.config.PasswordEncoder;
import xyz.tomorrowlearncamp.outsourcing.global.exception.InvalidRequestException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {


    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;


    @Test
    void user_entity_조회_성공() {
        //given
        Long id = 1L;
        UserEntity user = new UserEntity();
        ReflectionTestUtils.setField(user, "id", id);

        given(userRepository.findById(anyLong())).willReturn(Optional.of(user));

        // when
        UserEntity getUser = userService.getUserEntity(id);

        // then
        assertEquals(user.getId(), getUser.getId());
    }

    @Test
    void user_entity_조회_실패() {
        // given
        Long id = 1L;

        given(userRepository.findById(anyLong())).willReturn(Optional.empty());

        // when
        InvalidRequestException exception = assertThrows(InvalidRequestException.class,
                () -> userService.getUserEntity(id));

        // then
        assertEquals(ErrorUserMessage.NOT_FOUND_USER.getErrorMassage(), exception.getMessage());
    }

    @Test
    void user_dto_조회_성공() {
        //given
        Long id = 1L;
        UserEntity user = new UserEntity();
        ReflectionTestUtils.setField(user, "id", id);

        given(userRepository.findById(anyLong())).willReturn(Optional.of(user));

        // when
        InfoUserResponseDto responseDto = userService.getUser(id);

        // then
        assertEquals(user.getId(), responseDto.getUserId());
    }

    @Test
    void user_dto_조회_실패() { // given
        Long id = 1L;

        given(userRepository.findById(anyLong())).willReturn(Optional.empty());

        // when
        InvalidRequestException exception = assertThrows(InvalidRequestException.class,
                () -> userService.getUser(id));

        // then
        assertEquals(ErrorUserMessage.NOT_FOUND_USER.getErrorMassage(), exception.getMessage());
    }

    @Test
    void user_info_성공() {
        //given
        Long id = 1L;
        String nickname = "닉네임";
        String address = "주소";
        UserEntity user = new UserEntity();
        ReflectionTestUtils.setField(user, "id", id);
        ReflectionTestUtils.setField(user, "nickname", nickname);
        ReflectionTestUtils.setField(user, "address", address);

        given(userRepository.findById(anyLong())).willReturn(Optional.of(user));

        //when
        String updateNick = "닉네임1";
        String updateAddress = "주소1";

        userService.updateUserInfo(id, updateNick, updateAddress);
        UserEntity updateUser = userService.getUserEntity(id);

        //then
        assertEquals(updateNick, updateUser.getNickname());
        assertEquals(updateAddress, updateUser.getAddress());
    }

    @Test
    void user_info_실패() {
        Long id = 1L;
        String nickname = "닉네임";
        String address = "주소";

        given(userRepository.findById(anyLong())).willReturn(Optional.empty());

        // when
        InvalidRequestException exception = assertThrows(InvalidRequestException.class,
                () -> userService.updateUserInfo(id, nickname, address));

        // then
        assertEquals(ErrorUserMessage.NOT_FOUND_USER.getErrorMassage(), exception.getMessage());
    }

    @Test
    void user_password_변경_성공() {
        //given
        Long id = 1L;
        String password = "qwer1234!Q";
        String newPassword = "qwer1234!Q!Q!Q";
        UserEntity user = new UserEntity();
        ReflectionTestUtils.setField(user, "id", id);
        ReflectionTestUtils.setField(user, "password", password);

        given(userRepository.findById(anyLong())).willReturn(Optional.of(user));
        given(passwordEncoder.matches(anyString(), anyString())).willReturn(true);
        given(passwordEncoder.encode(anyString())).willReturn(newPassword);

        //when
        userService.updateUserPassword(id, password, newPassword);

        //then
        UserEntity updateUser = userService.getUserEntity(id);
        assertEquals(newPassword, updateUser.getPassword());
    }

    @Test
    void user_password_변경_실패_없는유저() {
        Long id = 1L;
        String password = "qwer1234!Q";
        String newPassword = "qwer1234!Q!Q!Q";

        given(userRepository.findById(anyLong())).willReturn(Optional.empty());

        // when
        InvalidRequestException exception = assertThrows(InvalidRequestException.class,
                () -> userService.updateUserPassword(id, password, newPassword));

        // then
        assertEquals(ErrorUserMessage.NOT_FOUND_USER.getErrorMassage(), exception.getMessage());
    }

    @Test
    void user_password_변경_실패_같지않은_비밀번호() {
        //given
        Long id = 1L;
        String password = "qwer1234!Q";
        String newPassword = "qwer1234!Q!Q!Q";
        UserEntity user = new UserEntity();
        ReflectionTestUtils.setField(user, "id", id);
        ReflectionTestUtils.setField(user, "password", password);

        given(userRepository.findById(anyLong())).willReturn(Optional.of(user));
        given(passwordEncoder.matches(anyString(), anyString())).willReturn(false);

        // when
        InvalidRequestException exception = assertThrows(InvalidRequestException.class,
                () -> userService.updateUserPassword(id, password, newPassword));

        // then
        assertEquals(ErrorUserMessage.INVALID_PASSWORD.getErrorMassage(), exception.getMessage());
    }

    @Test
    void delete_user_성공() {
        //given
        Long id = 1L;
        String password = "qwer1234!Q";
        UserEntity user = new UserEntity();
        ReflectionTestUtils.setField(user, "id", id);
        ReflectionTestUtils.setField(user, "password", password);

        given(userRepository.findById(anyLong())).willReturn(Optional.of(user));
        given(passwordEncoder.matches(anyString(), anyString())).willReturn(true);
        doNothing().when(userRepository).delete(any(UserEntity.class));

        // when
        userService.deleteUser(id, password);

        // then
        verify(userRepository, times(1)).delete(user);
        verify(userRepository, times(1)).findById(id);
    }

    @Test
    void delete_user_실패_없는유저() {
        //given
        Long id = 1L;
        UserEntity user = new UserEntity();
        ReflectionTestUtils.setField(user, "id", id);

        given(userRepository.findById(anyLong())).willReturn(Optional.empty());

        // when
        InvalidRequestException exception = assertThrows(InvalidRequestException.class,
                () -> userService.deleteUser(id, "pass"));

        // then
        assertEquals(ErrorUserMessage.NOT_FOUND_USER.getErrorMassage(), exception.getMessage());
    }

    @Test
    void delete_user_실패_틀린비밀번호() {
        //given
        Long id = 1L;
        String password = "qwer1234!Q";
        UserEntity user = new UserEntity();
        ReflectionTestUtils.setField(user, "id", id);
        ReflectionTestUtils.setField(user, "password", password);

        given(userRepository.findById(anyLong())).willReturn(Optional.of(user));
        given(passwordEncoder.matches(anyString(), anyString())).willReturn(false);

        // when
        InvalidRequestException exception = assertThrows(InvalidRequestException.class,
                () -> userService.deleteUser(id, "qwer1234"));

        // then
        assertEquals(ErrorUserMessage.INVALID_PASSWORD.getErrorMassage(), exception.getMessage());
    }
}