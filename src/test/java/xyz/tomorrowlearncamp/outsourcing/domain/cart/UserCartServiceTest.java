package xyz.tomorrowlearncamp.outsourcing.domain.cart;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import xyz.tomorrowlearncamp.outsourcing.domain.cart.dto.request.AddToCartRequestDto;
import xyz.tomorrowlearncamp.outsourcing.domain.cart.dto.response.AddCartResponseDto;
import xyz.tomorrowlearncamp.outsourcing.domain.cart.dto.response.UserCartResponseDto;
import xyz.tomorrowlearncamp.outsourcing.domain.cart.entity.CartEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.cart.repository.CartRepository;
import xyz.tomorrowlearncamp.outsourcing.domain.cart.service.UserCartService;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.entity.MenuEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.entity.MenuType;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.repository.MenuRepository;
import xyz.tomorrowlearncamp.outsourcing.domain.store.entity.StoreEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.user.entity.UserEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.user.service.UserService;
import xyz.tomorrowlearncamp.outsourcing.global.exception.InvalidRequestException;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserCartServiceTest {

    @InjectMocks
    private UserCartService userCartService;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private MenuRepository menuRepository;

    @Mock
    private UserService userService;

    @Test
    public void 장바구니에_메뉴를_정상적으로_추가한다() {
        // given
        Long userId = 1L;
        Long menuId = 1L;
        int quantity = 2;

        UserEntity user = new UserEntity();
        StoreEntity store = new StoreEntity("Title",
                LocalTime.of(20,10),
                LocalTime.of(21,10),
                5000,
                user);
        MenuEntity menu = MenuEntity.builder()
                .menuName("소금빵")
                .menuContent("맛있는 소금빵")
                .menuPrice(3500)
                .menuStatus(MenuType.ACTIVE)
                .store(store)
                .build();
        CartEntity cart = new CartEntity(user, menu, quantity);

        when(userService.getUserEntity(userId)).thenReturn(user);
        when(menuRepository.findById(menuId)).thenReturn(Optional.of(menu));
        when(cartRepository.findByUserIdAndMenuMenuId(userId, menuId)).thenReturn(Optional.empty());
        when(cartRepository.save(any(CartEntity.class))).thenReturn(cart);

        // when
        AddCartResponseDto response = userCartService.addCartItem(userId, new AddToCartRequestDto(menuId, quantity));

        // then
        assertNotNull(response);
        assertEquals(menu.getMenuName(), response.getMenuName());
        assertEquals(quantity, response.getQuantity());
    }

    @Test
    public void 장바구니에_담긴_메뉴를_정상적으로_삭제한다() {
        // given
        Long userId = 1L;
        Long menuId = 1L;
        int quantity = 2;

        UserEntity user = new UserEntity();
        MenuEntity menu = new MenuEntity();
        CartEntity cart = new CartEntity(user, menu, quantity);

        when(cartRepository.findByUserIdAndMenuMenuId(userId, menuId)).thenReturn(Optional.of(cart));

        // when
        userCartService.removeCartItem(userId, menuId);

        // then
        verify(cartRepository, times(1)).delete(cart);
    }

    @Test
    public void 장바구니에_담긴_모든_메뉴를_정상적으로_삭제한다() {
        // given
        Long userId = 1L;

        // when
        userCartService.removeAllCartItem(userId);

        // then
        verify(cartRepository, times(1)).deleteByUserId(userId);
    }

    @Test
    public void 장바구니에_담긴_모든_메뉴를_조회한다() {
        // given
        Long userId = 1L;

        UserEntity user = new UserEntity();
        MenuEntity menu1 = new MenuEntity();
        MenuEntity menu2 = new MenuEntity();

        CartEntity cart1 = new CartEntity(user, menu1, 2);
        CartEntity cart2 = new CartEntity(user, menu2, 3);

        List<CartEntity> cartItems = List.of(cart1, cart2);

        when(cartRepository.findByUserId(userId)).thenReturn(cartItems);

        // when
        List<UserCartResponseDto> response = userCartService.getCart(userId);

        // then
        assertNotNull(response);
        assertEquals(cartItems.size(), response.size());
        assertEquals(cart1.getQuantity(), response.get(0).getQuantity());
        assertEquals(cart2.getQuantity(), response.get(1).getQuantity());
    }

    @Test
    public void 장바구니에_없는_메뉴를_추가하면_새로_생성된다() {
        // given
        Long userId = 1L;
        Long menuId = 1L;
        int quantity = 2;

        UserEntity user = new UserEntity();
        StoreEntity store = new StoreEntity("Title",
                LocalTime.of(20,10),
                LocalTime.of(21,10),
                5000,
                user);
        MenuEntity menu = MenuEntity.builder()
                .menuName("소금빵")
                .menuContent("맛있는 소금빵")
                .menuPrice(3500)
                .menuStatus(MenuType.ACTIVE)
                .store(store)
                .build();
        CartEntity newCart = new CartEntity(user, menu, quantity);

        when(userService.getUserEntity(userId)).thenReturn(user);
        when(menuRepository.findById(menuId)).thenReturn(Optional.of(menu));
        when(cartRepository.findByUserIdAndMenuMenuId(userId, menuId)).thenReturn(Optional.empty());
        when(cartRepository.save(any(CartEntity.class))).thenReturn(newCart);

        // when
        AddCartResponseDto response = userCartService.addCartItem(userId, new AddToCartRequestDto(menuId, quantity));

        // then
        assertNotNull(response);
        verify(cartRepository, times(1)).save(any(CartEntity.class));
    }

    @Test
    public void 장바구니에_이미_있는_메뉴를_추가하면_수량이_변경된다() {
        // given
        Long userId = 1L;
        Long menuId = 1L;
        int originalQuantity = 1;
        int newQuantity = 3;

        UserEntity user = new UserEntity();
        StoreEntity store = new StoreEntity("Title",
                LocalTime.of(20,10),
                LocalTime.of(21,10),
                5000,
                user);
        MenuEntity menu = MenuEntity.builder()
                .menuName("소금빵")
                .menuContent("맛있는 소금빵")
                .menuPrice(3500)
                .menuStatus(MenuType.ACTIVE)
                .store(store)
                .build();

        CartEntity existingCart = new CartEntity(user, menu, originalQuantity);

        when(userService.getUserEntity(userId)).thenReturn(user);
        when(menuRepository.findById(menuId)).thenReturn(Optional.of(menu));
        when(cartRepository.findByUserIdAndMenuMenuId(userId, menuId)).thenReturn(Optional.of(existingCart));

        // when
        userCartService.addCartItem(userId, new AddToCartRequestDto(menuId, newQuantity));

        // then
        assertEquals(originalQuantity + newQuantity, existingCart.getQuantity());
        verify(cartRepository, never()).save(any(CartEntity.class));
    }

    @Test
    public void 존재하지_않는_메뉴를_추가하면_예외가_발생한다() {
        // given
        Long userId = 1L;
        Long menuId = 999L;
        int quantity = 1;

        when(userService.getUserEntity(userId)).thenReturn(new UserEntity());
        when(menuRepository.findById(menuId)).thenReturn(Optional.empty());

        // when
        InvalidRequestException exception = assertThrows(InvalidRequestException.class, () -> {
            userCartService.addCartItem(userId, new AddToCartRequestDto(menuId, quantity));
        });

        // then
        assertEquals("메뉴를 찾을 수 없습니다.", exception.getMessage());
    }

}
