package xyz.tomorrowlearncamp.outsourcing.domain.menu.controller;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.dto.request.MenuAddRequestDto;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.dto.request.MenuUpdateRequestDto;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.dto.response.MenuAddResponseDto;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.dto.response.MenuResponseDto;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.dto.response.MenuUpdateResponseDto;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.service.MenuService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/menus")
public class MenuController {
    private final MenuService menuService;

    @PostMapping
    public ResponseEntity<MenuAddResponseDto> addMenu(@RequestBody MenuAddRequestDto menuAddRequestDto) {
        MenuAddResponseDto response = menuService.addMenu(menuAddRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{menuId}")
    public ResponseEntity<MenuResponseDto> findMenuById(@PathVariable Long menuId) {
        return ResponseEntity.ok(menuService.findById(menuId));
    }

    @PutMapping("/{menuId}")
    public ResponseEntity<MenuUpdateResponseDto> updateMenu(
            @PathVariable Long menuId,
            @RequestBody MenuUpdateRequestDto updateRequestDto) {
        return ResponseEntity.ok(menuService.updateMenu(menuId,updateRequestDto));
    }

    @DeleteMapping("/{menuId}")
    public void deleteMenuById(
            @PathVariable Long menuId
    ) {
        menuService.deleteMenuById(menuId);
    }

}
