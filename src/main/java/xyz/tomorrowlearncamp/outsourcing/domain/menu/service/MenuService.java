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
import xyz.tomorrowlearncamp.outsourcing.domain.store.entity.StoreEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.store.repository.StoreRepository;
import xyz.tomorrowlearncamp.outsourcing.global.exception.InvalidRequestException;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;

    @Transactional
    public AddMenuResponseDto addMenu(AddMenuRequestDto addMenuRequestDto, Long ownerId) {
        StoreEntity store = storeRepository.findById(addMenuRequestDto.getStoreId())
                .orElseThrow(
                        () -> new InvalidRequestException(MenuErrorMessage.NOT_ALLOWED_ADD_MENU.getErrorMessage())
                );

        MenuEntity menu = new MenuEntity(
                addMenuRequestDto.getMenuName(),
                addMenuRequestDto.getMenuContent(),
                addMenuRequestDto.getMenuPrice(),
                addMenuRequestDto.getMenuImageUrl(),
                MenuType.valueOf(addMenuRequestDto.getMenuStatus()),
                store
        );

        MenuEntity addMenu = menuRepository.save(menu);
        return AddMenuResponseDto.from(addMenu);
    }

    @Transactional(readOnly = true)
    public MenuResponseDto findById(Long menuId, Long ownerId) {
        MenuEntity menu = menuRepository.findById(menuId).orElseThrow(
                () -> new InvalidRequestException(MenuErrorMessage.NOT_FOUND_MENU.getErrorMessage())
        );
        if (!Objects.equals(ownerId, menu.getStore().getUser().getId())) {
            throw new InvalidRequestException("본인의 가게만 메뉴를 조회할 수 있습니다.");
        }
        return MenuResponseDto.from(menu);
    }

    @Transactional
    public UpdateMenuResponseDto updateMenu(Long menuId, UpdateMenuRequestDto updateRequestDto, Long ownerId) {
        MenuEntity menu = menuRepository.findById(menuId).orElseThrow(
                () -> new InvalidRequestException(MenuErrorMessage.NOT_FOUND_MENU.getErrorMessage())
        );

        if (!Objects.equals(ownerId, menu.getStore().getUser().getId())) {
            throw new InvalidRequestException("본인의 가게만 메뉴를 수정할 수 있습니다.");
        }

        menu.updateMenu(
                updateRequestDto.getMenuName(),
                updateRequestDto.getMenuContent(),
                updateRequestDto.getMenuPrice(),
                updateRequestDto.getMenuImageUrl(),
                MenuType.valueOf(updateRequestDto.getMenuStatus())
        );
        return UpdateMenuResponseDto.from(menu);
    }

    @Transactional
    public void deleteMenuById(Long menuId, Long ownerId) {
        MenuEntity menu = menuRepository.findById(menuId).orElseThrow(
                () -> new InvalidRequestException(MenuErrorMessage.NOT_FOUND_MENU.getErrorMessage())
        );

        if (!Objects.equals(ownerId, menu.getStore().getUser().getId())) {
            throw new InvalidRequestException("본인의 가게만 메뉴를 삭제할 수 있습니다.");
        }

        menu.deleteMenu();
    }
}
