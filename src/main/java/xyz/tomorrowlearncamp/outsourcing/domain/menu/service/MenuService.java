package xyz.tomorrowlearncamp.outsourcing.domain.menu.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.dto.request.AddMenuRequestDto;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.dto.request.UpdateMenuRequestDto;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.dto.response.AddMenuResponseDto;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.dto.response.MenuResponseDto;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.dto.response.UpdateMenuResponseDto;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.entity.MenuEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.entity.MenuType;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.enums.MenuErrorMessage;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.repository.MenuRepository;
import xyz.tomorrowlearncamp.outsourcing.global.exception.InvalidRequestException;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;

    @Transactional
    public AddMenuResponseDto addMenu(AddMenuRequestDto addMenuRequestDto) {
        MenuEntity menu = MenuEntity.builder()
                .menuName(addMenuRequestDto.getMenuName())
                .menuContent(addMenuRequestDto.getMenuContent())
                .menuPrice(addMenuRequestDto.getMenuPrice())
                .menuImageUrl(addMenuRequestDto.getMenuImageUrl())
                .menuStatus(MenuType.valueOf(addMenuRequestDto.getMenuStatus()))
                .build();
        MenuEntity addMenu = menuRepository.save(menu);
        return new AddMenuResponseDto(addMenu.getId(), addMenu.getMenuName(), addMenu.getMenuContent(), addMenu.getMenuPrice(), addMenu.getMenuImageUrl());
    }

    @Transactional(readOnly = true)
    public MenuResponseDto findById(Long menuId) {
        MenuEntity menu  = menuRepository.findById(menuId).orElseThrow(
                () -> new InvalidRequestException(MenuErrorMessage.NOT_FOUND_MENU.getErrorMessage())
        );
        return MenuResponseDto.from(menu);
    }

    @Transactional
    public UpdateMenuResponseDto updateMenu(Long menuId, UpdateMenuRequestDto updateRequestDto) {
        MenuEntity menu = menuRepository.findById(menuId).orElseThrow(
                () -> new InvalidRequestException(MenuErrorMessage.NOT_FOUND_MENU.getErrorMessage())
        );

        menu.updateMenu(updateRequestDto.getMenuName(),updateRequestDto.getMenuContent(),updateRequestDto.getMenuPrice(),updateRequestDto.getMenuImageUrl(), MenuType.valueOf(updateRequestDto.getMenuStatus()));
        return new UpdateMenuResponseDto(menu.getId(), menu.getMenuName(), menu.getMenuContent(),menu.getMenuPrice(),menu.getMenuImageUrl(),menu.getMenuStatus());
    }

    @Transactional
    public void deleteMenuById(Long menuId){
        MenuEntity menu = menuRepository.findById(menuId).orElseThrow(
                () -> new InvalidRequestException(MenuErrorMessage.NOT_FOUND_MENU.getErrorMessage())
        );
        menu.deleteMenu();
    }
}
