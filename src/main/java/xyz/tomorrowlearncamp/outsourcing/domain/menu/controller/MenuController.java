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
import xyz.tomorrowlearncamp.outsourcing.global.annotation.Auth;
import xyz.tomorrowlearncamp.outsourcing.global.entity.AuthUser;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/menus")
public class MenuController {
    private final MenuService menuService;

    @PostMapping
    public ResponseEntity<AddMenuResponseDto> addMenu(
            @Auth AuthUser owner,
            @Valid @RequestBody AddMenuRequestDto addMenuRequestDto) {
        AddMenuResponseDto response = menuService.addMenu(addMenuRequestDto, owner.getId());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{menuId}")
    public ResponseEntity<MenuResponseDto> findMenuById(
            @Auth AuthUser owner,
            @PathVariable Long menuId) {
        return ResponseEntity.ok(menuService.findById(menuId, owner.getId()));
    }

    @PutMapping("/{menuId}")
    public ResponseEntity<UpdateMenuResponseDto> updateMenu(
            @Auth AuthUser owner,
            @PathVariable Long menuId,
            @Valid @RequestBody UpdateMenuRequestDto updateRequestDto) {
        return ResponseEntity.ok(menuService.updateMenu(menuId, updateRequestDto, owner.getId()));
    }

    @DeleteMapping("/{menuId}")
    public void deleteMenuById(
            @Auth AuthUser owner,
            @PathVariable Long menuId
    ) {
        menuService.deleteMenuById(menuId, owner.getId());
    }

}
