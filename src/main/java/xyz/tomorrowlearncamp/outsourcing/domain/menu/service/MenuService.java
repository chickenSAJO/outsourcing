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
import xyz.tomorrowlearncamp.outsourcing.domain.menu.enums.ErrorMenuMessage;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.repository.MenuRepository;
import xyz.tomorrowlearncamp.outsourcing.domain.store.entity.StoreEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.store.enums.StoreErrorMessage;
import xyz.tomorrowlearncamp.outsourcing.domain.store.repository.StoreRepository;
import xyz.tomorrowlearncamp.outsourcing.domain.user.entity.UserEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.user.repository.UserRepository;
import xyz.tomorrowlearncamp.outsourcing.global.exception.InvalidRequestException;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    @Transactional
    public AddMenuResponseDto addMenu(AddMenuRequestDto addMenuRequestDto, Long userId) {
        StoreEntity store = storeRepository.findById(addMenuRequestDto.getStoreId())
                .orElseThrow(
                        () -> new InvalidRequestException(StoreErrorMessage.NOT_FOUND_STORE.getErrorMessage())
                );

        // 가게 소유권 검증 추가
        UserEntity owner = userRepository.findById(userId)
                .orElseThrow(() -> new InvalidRequestException("사용자를 찾을 수 없습니다."));

        if (!Objects.equals(userId, owner.getId())) {
            throw new InvalidRequestException(ErrorMenuMessage.NOT_ALLOWED_ADD_MENU.getErrorMessage());
        }

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
    public MenuResponseDto findById(Long menuId, Long userId) {
        MenuEntity menu = menuRepository.findById(menuId).orElseThrow(
                () -> new InvalidRequestException(ErrorMenuMessage.NOT_FOUND_MENU.getErrorMessage())
        );
        if (!Objects.equals(userId, menu.getStore().getUser().getId())) {
            throw new InvalidRequestException(ErrorMenuMessage.NOT_ALLOWED_VIEW_MENU.getErrorMessage());
        }
        return MenuResponseDto.from(menu);
    }

    @Transactional
    public UpdateMenuResponseDto updateMenu(Long menuId, UpdateMenuRequestDto updateRequestDto, Long userId) {
        MenuEntity menu = menuRepository.findById(menuId).orElseThrow(
                () -> new InvalidRequestException(ErrorMenuMessage.NOT_FOUND_MENU.getErrorMessage())
        );

        if (!Objects.equals(userId, menu.getStore().getUser().getId())) {
            throw new InvalidRequestException(ErrorMenuMessage.NOT_ALLOWED_UPDATE_MENU.getErrorMessage());
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
    public void deleteMenuById(Long menuId, Long userId) {
        MenuEntity menu = menuRepository.findById(menuId).orElseThrow(
                () -> new InvalidRequestException(ErrorMenuMessage.NOT_FOUND_MENU.getErrorMessage())
        );

        if (!Objects.equals(userId, menu.getStore().getUser().getId())) {
            throw new InvalidRequestException(ErrorMenuMessage.NOT_ALLOWED_REMOVE_MENU.getErrorMessage());
        }

        menu.deleteMenu();
    }
}
