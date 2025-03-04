package xyz.tomorrowlearncamp.outsourcing.domain.menu.controller;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.dto.request.MenuAddRequestDto;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.dto.response.MenuAddResponseDto;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.service.MenuService;

@RestController
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;

    @PostMapping("/api/v1/menus")
    public ResponseEntity<MenuAddResponseDto> addMenu(@RequestBody MenuAddRequestDto menuAddRequestDto){
        MenuAddResponseDto response = menuService.addMenu(menuAddRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
