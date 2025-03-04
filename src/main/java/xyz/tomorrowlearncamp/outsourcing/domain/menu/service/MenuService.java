package xyz.tomorrowlearncamp.outsourcing.domain.menu.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.dto.request.MenuAddRequestDto;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.dto.response.MenuAddResponseDto;
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
        return new MenuAddResponseDto(addMenu.getId(), addMenu.getMenuName(), addMenu.getMenuContent(), addMenu.getMenuImageUrl(), addMenu.getPrice());
    }
}
