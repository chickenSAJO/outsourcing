package xyz.tomorrowlearncamp.outsourcing.domain.menu.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.tomorrowlearncamp.outsourcing.domain.common.annotation.Auth;
import xyz.tomorrowlearncamp.outsourcing.domain.common.dto.AuthUser;
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
            @Auth AuthUser user,
            @Valid @RequestBody AddMenuRequestDto addMenuRequestDto) {
        AddMenuResponseDto response = menuService.addMenu(addMenuRequestDto,user.getId());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{menuId}")
    public ResponseEntity<MenuResponseDto> findMenuById(
            @Auth AuthUser user,
            @PathVariable Long menuId) {
        return ResponseEntity.ok(menuService.findById(menuId, user.getId()));
    }

    @PutMapping("/{menuId}")
    public ResponseEntity<UpdateMenuResponseDto> updateMenu(
            @Auth AuthUser user,
            @PathVariable Long menuId,
            @Valid @RequestBody UpdateMenuRequestDto updateRequestDto) {
        return ResponseEntity.ok(menuService.updateMenu(menuId, updateRequestDto, user.getId()));
    }

    @DeleteMapping("/{menuId}")
    public void deleteMenuById(
            @Auth AuthUser user,
            @PathVariable Long menuId
    ) {
        menuService.deleteMenuById(menuId, user.getId());
    }

}
