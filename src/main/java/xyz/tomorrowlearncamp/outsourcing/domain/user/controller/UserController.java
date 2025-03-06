package xyz.tomorrowlearncamp.outsourcing.domain.user.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.tomorrowlearncamp.outsourcing.domain.user.dto.request.UpdateInfoUserRequestDto;
import xyz.tomorrowlearncamp.outsourcing.domain.user.dto.request.UpdatePasswordUserRequestDto;
import xyz.tomorrowlearncamp.outsourcing.domain.user.dto.response.InfoUserResponseDto;
import xyz.tomorrowlearncamp.outsourcing.domain.user.service.UserService;
import xyz.tomorrowlearncamp.outsourcing.global.annotation.Auth;
import xyz.tomorrowlearncamp.outsourcing.global.entity.AuthUser;
import xyz.tomorrowlearncamp.outsourcing.global.etc.RegexpType;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("")
    public ResponseEntity<InfoUserResponseDto> getUser(
            @Auth AuthUser user
    ) {
        InfoUserResponseDto responseDto = userService.getUser(user.getId());

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PatchMapping("")
    public ResponseEntity<Void> patchInfoUser(
            @Auth AuthUser user,
            @Valid @RequestBody UpdateInfoUserRequestDto requestDto
    ) {
        userService.updateUserInfo(user.getId(), requestDto.getNickname(), requestDto.getAddress());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/password")
    public ResponseEntity<Void> patchPasswordUser(
            @Auth AuthUser user,
            @Valid @RequestBody UpdatePasswordUserRequestDto requestDto
    ) {
        userService.updateUserPassword(user.getId(), requestDto.getOldPassword(), requestDto.getNewPassword());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("")
    public ResponseEntity<Void> deleteUser(
            @Auth AuthUser user,
            @Pattern(regexp = RegexpType.PASSWORD) @RequestHeader String password
    ) {
        userService.deleteUser(user.getId(), password);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
