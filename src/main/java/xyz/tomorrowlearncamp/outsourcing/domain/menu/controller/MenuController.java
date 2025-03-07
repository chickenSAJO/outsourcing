package xyz.tomorrowlearncamp.outsourcing.domain.menu.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.tomorrowlearncamp.outsourcing.auth.annotaion.Auth;
import xyz.tomorrowlearncamp.outsourcing.auth.dto.AuthUser;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.dto.request.AddMenuRequestDto;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.dto.request.UpdateMenuRequestDto;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.dto.response.AddMenuResponseDto;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.dto.response.MenuResponseDto;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.dto.response.UpdateMenuResponseDto;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.service.MenuService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MenuController {
    private final MenuService menuService;

    @PostMapping("/v1/menus")
    public ResponseEntity<AddMenuResponseDto> addMenu(
            @Auth AuthUser user,
            @Valid @RequestBody AddMenuRequestDto addMenuRequestDto) {
        AddMenuResponseDto response = menuService.addMenu(addMenuRequestDto, user.getId());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/v1/menus/{menuId}")
    public ResponseEntity<MenuResponseDto> findMenuById(
            @SessionAttribute(name = "LOGIN_USER") Long ownerId,
            @PathVariable Long menuId) {
        return ResponseEntity.ok(menuService.findById(menuId, ownerId));
    }

    @PutMapping("/v1/menus/{menuId}")
    public ResponseEntity<UpdateMenuResponseDto> updateMenu(
            @Auth AuthUser user,
            @PathVariable Long menuId,
            @Valid @RequestBody UpdateMenuRequestDto updateRequestDto) {
        return ResponseEntity.ok(menuService.updateMenu(menuId, updateRequestDto, user.getId()));
    }

    @DeleteMapping("/v1/menus/{menuId}")
    public void deleteMenuById(
            @Auth AuthUser user,
            @PathVariable Long menuId
    ) {
        menuService.deleteMenuById(menuId, user.getId());
    }

}
