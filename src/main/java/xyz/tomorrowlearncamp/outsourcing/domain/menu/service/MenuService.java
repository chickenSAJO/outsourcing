package xyz.tomorrowlearncamp.outsourcing.domain.menu.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.dto.request.MenuAddRequestDto;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.dto.request.MenuUpdateRequestDto;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.dto.response.MenuAddResponseDto;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.dto.response.MenuResponseDto;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.dto.response.MenuUpdateResponseDto;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.entity.Menu;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.repository.MenuRepository;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;

    @Transactional
    public MenuAddResponseDto addMenu(MenuAddRequestDto menuAddRequestDto) {
        Menu menu = new Menu(menuAddRequestDto.getMenuName(), menuAddRequestDto.getMenuContent(), menuAddRequestDto.getPrice(), menuAddRequestDto.getMenuImageUrl(), menuAddRequestDto.getMenuStatus());
        Menu addMenu = menuRepository.save(menu);
        return new MenuAddResponseDto(addMenu.getId(), addMenu.getMenuName(), addMenu.getMenuContent(), addMenu.getPrice(), addMenu.getMenuImageUrl());
    }

    @Transactional(readOnly = true)
    public MenuResponseDto findById(Long menuId) {
        Menu menu  = menuRepository.findById(menuId).orElseThrow(
                () -> new IllegalArgumentException("메뉴 없음")
        );
        return new MenuResponseDto(
                menu.getId(),
                menu.getMenuName(),
                menu.getMenuContent(),
                menu.getPrice(),
                menu.getMenuImageUrl(),
                menu.getMenuStatus()
        );
    }

    @Transactional
    public MenuUpdateResponseDto update(Long menuId, MenuUpdateRequestDto updateRequestDto) {
        Menu menu = menuRepository.findById(menuId).orElseThrow(
                () -> new IllegalArgumentException("메뉴 없음")
        );

        menu.update(updateRequestDto.getMenuName(),updateRequestDto.getMenuContent(),updateRequestDto.getPrice(),updateRequestDto.getMenuImageUrl(),updateRequestDto.getMenuStatus());
        return new MenuUpdateResponseDto(menu.getId(), menu.getMenuName(), menu.getMenuContent(),menu.getPrice(),menu.getMenuImageUrl(),menu.getMenuStatus());
    }

    @Transactional
    public void deleteById(Long menuId){
        Menu menu = menuRepository.findById(menuId).orElseThrow(
                () -> new IllegalArgumentException("메뉴 없음")
        );
        menuRepository.deleteById(menuId);
    }
}
