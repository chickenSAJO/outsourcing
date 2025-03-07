package xyz.tomorrowlearncamp.outsourcing.domain.menu.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.dto.request.AddMenuRequestDto;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.dto.request.UpdateMenuRequestDto;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.dto.response.AddMenuResponseDto;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.dto.response.MenuResponseDto;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.dto.response.UpdateMenuResponseDto;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.entity.MenuEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.entity.MenuType;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.repository.MenuRepository;
import xyz.tomorrowlearncamp.outsourcing.domain.store.entity.StoreEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.store.repository.StoreRepository;
import xyz.tomorrowlearncamp.outsourcing.domain.user.entity.UserEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.user.enums.Usertype;
import xyz.tomorrowlearncamp.outsourcing.domain.user.repository.UserRepository;
import xyz.tomorrowlearncamp.outsourcing.global.exception.InvalidRequestException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MenuServiceTest {

    @Mock
    private MenuRepository menuRepository;

    @Mock
    private StoreRepository storeRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private MenuService menuService;

    @Test
    void 메뉴생성에_성공한다() {
        // given
        Long storeId = 1L; // storeId 설정
        Long ownerId = 1L; // ownerId 설정
        Long menuId = 1L; // menuId 설정

        UserEntity user = new UserEntity(
                "SPY@gmail.com",
                "Qwerty1234!",
                "010-1234-5678",
                "사장닉네임",
                "사장명",
                "서울시 강남구",
                Usertype.OWNER
        );
        ReflectionTestUtils.setField(user, "id", ownerId); // user id 설정

        AddMenuRequestDto addMenuRequestDto = new AddMenuRequestDto(
                "짬뽕",
                "불맛 짬뽕",
                8000,
                "",
                "ACTIVE",
                storeId
        );

        StoreEntity store = new StoreEntity();
        store.setUser(user);
        store.setStoreId(storeId);
        MenuEntity savedMenu = new MenuEntity(1L, "짬뽕", "불맛 짬뽕", 8000, "", MenuType.ACTIVE, store);
        ReflectionTestUtils.setField(savedMenu, "menuId", menuId);

        given(menuRepository.save(any(MenuEntity.class)))
                .willAnswer(invocation -> {
                    MenuEntity menu = invocation.getArgument(0);
                    ReflectionTestUtils.setField(menu, "menuId", 1L); // ReflectionTestUtils 사용
                    return menu;
                });

        given(userRepository.findById(ownerId)).willReturn(Optional.of(user));
        given(storeRepository.findById(storeId)).willReturn(Optional.of(store));

        // when
        AddMenuResponseDto result = menuService.addMenu(addMenuRequestDto, ownerId);

        // then
        assertEquals(1L, result.getId());
    }



    @Test
    void 메뉴조회에_성공한다() {
        //given
        Long storeId = 1L; // storeId 설정
        Long ownerId = 1L; // ownerId 설정
        Long menuId = 1L; // menuId 설정

        UserEntity user = new UserEntity(
                "SPY@gmail.com",
                "Qwerty1234!",
                "010-1234-5678",
                "사장닉네임",
                "사장명",
                "서울시 강남구",
                Usertype.OWNER
        );
        ReflectionTestUtils.setField(user, "id", ownerId); // user id 설정

        StoreEntity store = new StoreEntity();
        store.setUser(user);
        store.setStoreId(storeId);

        MenuEntity menu = new MenuEntity(menuId, "짬뽕", "불맛 짬뽕", 8000, "", MenuType.ACTIVE, store);

        given(menuRepository.findById(menuId)).willReturn(Optional.of(menu));

        //when
        MenuResponseDto result = menuService.findById(menuId, ownerId);

        //then
        assertEquals(menuId, result.getId());
        assertEquals("짬뽕", result.getMenuName());
        assertEquals("불맛 짬뽕", result.getMenuContent());
        assertEquals(8000, result.getMenuPrice());
        assertEquals(MenuType.ACTIVE, result.getMenuStatus());
    }

    @Test
    void 메뉴조회에_실패한다_메뉴없음() {
        // given
        Long ownerId = 1L;
        Long menuId = 1L;

        given(menuRepository.findById(menuId)).willReturn(Optional.empty());

        // when, then
        assertThrows(InvalidRequestException.class, () -> menuService.findById(menuId, ownerId));
    }

    @Test
    void 메뉴조회에_실패한다_권한없음() {
        // given
        Long storeId = 1L;
        Long ownerId = 1L;
        Long menuId = 1L;
        Long otherOwnerId = 2L;

        UserEntity user = new UserEntity(
                "SPY@gmail.com",
                "Qwerty1234!",
                "010-1234-5678",
                "사장닉네임",
                "사장명",
                "서울시 강남구",
                Usertype.OWNER
        );
        ReflectionTestUtils.setField(user, "id", ownerId); // user id 설정

        StoreEntity store = new StoreEntity();
        store.setUser(user);
        store.setStoreId(storeId);

        MenuEntity menu = new MenuEntity(menuId, "짬뽕", "불맛 짬뽕", 8000, "", MenuType.ACTIVE, store);

        given(menuRepository.findById(menuId)).willReturn(Optional.of(menu));

        // when, then
        assertThrows(InvalidRequestException.class, () -> menuService.findById(menuId, otherOwnerId));
    }

    @Test
    void 메뉴수정에_성공한다() {
        // given
        Long storeId = 1L;
        Long ownerId = 1L;
        Long menuId = 1L;

        UserEntity user = new UserEntity(
                "SPY@gmail.com",
                "Qwerty1234!",
                "010-1234-5678",
                "사장닉네임",
                "사장명",
                "서울시 강남구",
                Usertype.OWNER
        );
        ReflectionTestUtils.setField(user, "id", ownerId); // user id 설정

        StoreEntity store = new StoreEntity();
        store.setUser(user);
        store.setStoreId(storeId);

        MenuEntity menu = new MenuEntity(menuId, "짬뽕", "불맛 짬뽕", 8000, "", MenuType.ACTIVE, store);

        given(menuRepository.findById(menuId)).willReturn(Optional.of(menu));

        UpdateMenuRequestDto updateMenuRequestDto = new UpdateMenuRequestDto(
                "짜장면",
                "맛있는 짜장면",
                7000,
                "",
                "ACTIVE"
        );

        // when
        UpdateMenuResponseDto result = menuService.updateMenu(menuId, updateMenuRequestDto, ownerId);

        // then
        assertEquals(menuId, result.getId());
        assertEquals("짜장면", result.getMenuName());
        assertEquals("맛있는 짜장면", result.getMenuContent());
        assertEquals(7000, result.getMenuPrice());
        assertEquals(MenuType.ACTIVE, result.getMenuStatus());
    }

    @Test
    void 메뉴수정에_실패한다_메뉴없음() {
        // given
        Long ownerId = 1L;
        Long menuId = 1L;

        given(menuRepository.findById(menuId)).willReturn(Optional.empty());

        UpdateMenuRequestDto updateMenuRequestDto = new UpdateMenuRequestDto(
                "짜장면",
                "맛있는 짜장면",
                7000,
                "",
                "ACTIVE"
        );

        // when, then
        assertThrows(InvalidRequestException.class, () -> menuService.updateMenu(menuId, updateMenuRequestDto, ownerId));
    }

    @Test
    void 메뉴삭제에_성공한다() {
        // given
        Long storeId = 1L;
        Long ownerId = 1L;
        Long menuId = 1L;

        UserEntity user = new UserEntity(
                "SPY@gmail.com",
                "Qwerty1234!",
                "010-1234-5678",
                "사장닉네임",
                "사장명",
                "서울시 강남구",
                Usertype.OWNER
        );
        ReflectionTestUtils.setField(user, "id", ownerId); // user id 설정

        StoreEntity store = new StoreEntity();
        store.setUser(user);
        store.setStoreId(storeId);
        MenuEntity menu = new MenuEntity(menuId, "짬뽕", "불맛 짬뽕", 8000, "", MenuType.ACTIVE, store);
        MenuEntity deletedMenu = new MenuEntity(menuId, "짬뽕", "불맛 짬뽕", 8000, "", MenuType.DELETED, store); // 삭제된 메뉴 객체 생성

        given(menuRepository.findById(menuId))
                .willReturn(Optional.of(menu))
                .willReturn(Optional.of(deletedMenu)); // 삭제 후 DELETED 상태의 메뉴 반환 설정

        // when
        menuService.deleteMenuById(menuId, ownerId);

        // then
        MenuEntity result = menuRepository.findById(menuId).orElse(null);
        assert result != null;
        assertEquals(MenuType.DELETED, result.getMenuStatus());
    }
}