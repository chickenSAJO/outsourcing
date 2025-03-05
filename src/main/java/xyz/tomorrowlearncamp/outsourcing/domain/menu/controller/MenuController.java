package xyz.tomorrowlearncamp.outsourcing.domain.menu.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.dto.request.AddMenuRequestDto;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.dto.request.UpdateMenuRequestDto;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.dto.response.AddMenuResponseDto;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.dto.response.MenuResponseDto;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.dto.response.UpdateMenuResponseDto;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.service.MenuService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/menus")
public class MenuController {
    private final MenuService menuService;

    @PostMapping
    public ResponseEntity<AddMenuResponseDto> addMenu(
            //@SessionAttribute(name = "LOGIN_USER") Long userId, todo: 세션 기반 인증 구현
            @Valid @RequestBody AddMenuRequestDto addMenuRequestDto) {
        AddMenuResponseDto response = menuService.addMenu(addMenuRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{menuId}")
    public ResponseEntity<MenuResponseDto> findMenuById(@PathVariable Long menuId) {
        return ResponseEntity.ok(menuService.findById(menuId));
    }

    @PutMapping("/{menuId}")
    public ResponseEntity<UpdateMenuResponseDto> updateMenu(
            //@SessionAttribute(name = "LOGIN_USER") Long userId, todo: 세션 기반 인증 구현
            @PathVariable Long menuId,
            @Valid @RequestBody UpdateMenuRequestDto updateRequestDto) {
        return ResponseEntity.ok(menuService.updateMenu(menuId, updateRequestDto));
    }

    @DeleteMapping("/{menuId}")
    public void deleteMenuById(
            //@SessionAttribute(name = "LOGIN_USER") Long userId, todo: 세션 기반 인증 구현
            @PathVariable Long menuId
    ) {
        menuService.deleteMenuById(menuId);
    }

}
