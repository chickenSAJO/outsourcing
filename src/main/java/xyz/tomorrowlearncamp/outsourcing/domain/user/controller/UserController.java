package xyz.tomorrowlearncamp.outsourcing.domain.user.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.tomorrowlearncamp.outsourcing.domain.user.dto.request.PatchInfoUserRequestDto;
import xyz.tomorrowlearncamp.outsourcing.domain.user.dto.request.PatchPasswordUserRequestDto;
import xyz.tomorrowlearncamp.outsourcing.domain.user.dto.response.InfoUserResponseDto;
import xyz.tomorrowlearncamp.outsourcing.domain.user.service.UserService;
import xyz.tomorrowlearncamp.outsourcing.global.etc.RegexpType;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("")
    public ResponseEntity<InfoUserResponseDto> getUser(
            @SessionAttribute(name = "LOGIN_USER") Long loginUser
    ) {
        InfoUserResponseDto responseDto = userService.getUser(loginUser);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PatchMapping("")
    public ResponseEntity<Void> patchInfoUser(
            @SessionAttribute(name = "LOGIN_USER") Long loginUser,
            @Valid @RequestBody PatchInfoUserRequestDto requestDto
    ) {
        userService.patchInfoUser(loginUser, requestDto.getNickname(), requestDto.getAddress());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/password")
    public ResponseEntity<Void> patchPasswordUser(
            @SessionAttribute(name = "LOGIN_USER") Long loginUser,
            @Valid @RequestBody PatchPasswordUserRequestDto requestDto
    ) {
        userService.patchPasswordUser(loginUser, requestDto.getOldPassword(), requestDto.getNewPassword());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("")
    public ResponseEntity<Void> deleteUser(
            @SessionAttribute(name = "LOGIN_USER") Long loginUser,
            @Pattern(regexp = RegexpType.PASSWORD) @RequestHeader String password
    ) {
        userService.deleteUser(loginUser, password);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
