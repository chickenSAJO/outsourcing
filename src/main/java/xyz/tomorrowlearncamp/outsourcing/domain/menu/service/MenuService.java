package xyz.tomorrowlearncamp.outsourcing.domain.menu.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.dto.request.MenuAddRequestDto;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.dto.request.MenuUpdateRequestDto;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.dto.response.MenuAddResponseDto;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.dto.response.MenuResponseDto;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.dto.response.MenuUpdateResponseDto;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.entity.MenuEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.entity.MenuType;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.exception.MenuNotFoundException;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.repository.MenuRepository;

import java.awt.*;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;

    @Transactional
    public MenuAddResponseDto addMenu(MenuAddRequestDto menuAddRequestDto) {
        MenuEntity menu = MenuEntity.builder()
                .menuName(menuAddRequestDto.getMenuName())
                .menuContent(menuAddRequestDto.getMenuContent())
                .menuPrice(menuAddRequestDto.getMenuPrice())
                .menuImageUrl(menuAddRequestDto.getMenuImageUrl())
                .menuStatus(MenuType.valueOf(menuAddRequestDto.getMenuStatus()))
                .build();
        MenuEntity addMenu = menuRepository.save(menu);
        return new MenuAddResponseDto(addMenu.getId(), addMenu.getMenuName(), addMenu.getMenuContent(), addMenu.getMenuPrice(), addMenu.getMenuImageUrl());
    }

    @Transactional(readOnly = true)
    public MenuResponseDto findById(Long menuId) {
        MenuEntity menu  = menuRepository.findById(menuId).orElseThrow(
                () -> new MenuNotFoundException()
        );
        return MenuResponseDto.from(menu);
    }

    @Transactional
    public MenuUpdateResponseDto updateMenu(Long menuId, MenuUpdateRequestDto updateRequestDto) {
        MenuEntity menu = menuRepository.findById(menuId).orElseThrow(
                () -> new MenuNotFoundException()
        );

        menu.updateMenu(updateRequestDto.getMenuName(),updateRequestDto.getMenuContent(),updateRequestDto.getMenuPrice(),updateRequestDto.getMenuImageUrl(), MenuType.valueOf(updateRequestDto.getMenuStatus()));
        return new MenuUpdateResponseDto(menu.getId(), menu.getMenuName(), menu.getMenuContent(),menu.getMenuPrice(),menu.getMenuImageUrl(),menu.getMenuStatus());
    }

    @Transactional
    public void deleteMenuById(Long menuId){
        MenuEntity menu = menuRepository.findById(menuId).orElseThrow(
                () -> new MenuNotFoundException()
        );
        menu.deleteMenu();
    }
}
