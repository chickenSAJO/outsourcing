package xyz.tomorrowlearncamp.outsourcing.domain.store.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpSession;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.dto.response.MenuResponseDto;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.entity.MenuType;
import xyz.tomorrowlearncamp.outsourcing.domain.store.dto.*;
import xyz.tomorrowlearncamp.outsourcing.domain.store.entity.StoreEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.store.repository.StoreRepository;
import xyz.tomorrowlearncamp.outsourcing.domain.user.entity.UserEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.user.enums.Usertype;
import xyz.tomorrowlearncamp.outsourcing.domain.user.repository.UserRepository;
import xyz.tomorrowlearncamp.outsourcing.domain.user.service.UserService;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StoreServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private StoreRepository storeRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private StoreService storeService;

    //saveStore 테스트
    @Test
    public void 가게생성에_성공한다() {
        // given
        long userId = 1L;
        UserEntity mockUser = new UserEntity(
                "test@naver.com",
                "Test1234!23",
                "010-1234-5678",
                "사장닉네임",
                "사장명",
                "서울시 강남구",
                Usertype.OWNER
        );
        SaveStoreRequestDto saveStoreRequestDto = new SaveStoreRequestDto(
                "국밥맛집",
                "09:00:00",
                "21:00:00",
                15000
        );
        HttpSession mockSession = mock(HttpSession.class);
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        given(mockRequest.getSession()).willReturn(mockSession);
        given(mockSession.getAttribute("userId")).willReturn(userId);

        given(userService.getUserEntity(userId)).willReturn(mockUser);
        given(storeRepository.existsByStoreTitle(saveStoreRequestDto.getStoreTitle())).willReturn(false);//가게 이름으로 가게 있는지 확인
        given(userRepository.existsByEmail(mockUser.getEmail())).willReturn(true);//이메일을 알맞게 입력 받았는지 확인
        given(userRepository.findByEmail(mockUser.getEmail())).willReturn(Optional.of(mockUser));//이메일이 이미 있는지 확인

        userService.getUserEntity(1L);

        StoreEntity savedStore = makeStoreEntity(mockUser);
        savedStore.setStoreId(1L);

        given(storeRepository.save(any(StoreEntity.class))).willReturn(savedStore);

        // when
        SaveStoreResponseDto result = storeService.saveStore(mockUser.getId(), saveStoreRequestDto);//storeService의 saveStore을 테스트

        // when & then
        assertEquals(1L, result.getStoreId());
        assertEquals("국밥맛집", result.getStoreTitle());
        assertEquals(LocalTime.of(9, 0), result.getOpenTime());
        assertEquals(LocalTime.of(21, 0), result.getCloseTime());
        assertEquals(15000, result.getMinimumOrder());
    }



    //updateStore 테스트
    @Test
    public void 가게_업데이트에_성공한다() {
        // given
        Long userId = 1L;
        Long storeId = 1L;
        UserEntity mockUser = new UserEntity(
                "test@naver.com",
                "Test1234!23",
                "010-1234-5678",
                "사장닉네임",
                "사장명",
                "서울시 강남구",
                Usertype.OWNER
        );
        UpdateStoreRequestDto updateStoreRequestDto = new UpdateStoreRequestDto(
                "국밥맛집2",
                "12:00:00",
                "22:00:00",
                20000
        );

        given(storeRepository.existsByStoreTitle(updateStoreRequestDto.getStoreTitle())).willReturn(false);//가게 이름 확인?
        given(userRepository.existsByEmail(mockUser.getEmail())).willReturn(true);//이메일을 알맞게 입력 받았는지 확인
        given(userRepository.findByEmail(mockUser.getEmail())).willReturn(Optional.of(mockUser));//이메일이 이미 있는지 확인

        StoreEntity savedStore = makeStoreEntity(mockUser);
        savedStore.setStoreId(storeId);

        given(storeRepository.findById(storeId)).willReturn(Optional.of(savedStore));
        given(storeRepository.save(any(StoreEntity.class))).willAnswer(invocation -> {
            StoreEntity store = invocation.getArgument(0);
            store.setStoreId(storeId);
            return store;
        });//id가 자동 생성된다고 가정

        // when
        UpdateStoreResponseDto result = storeService.updateStore(userId, storeId, updateStoreRequestDto);

        // when & then
        assertEquals(storeId, result.getStoreId());
        assertEquals("국밥맛집2", result.getStoreTitle());
        assertEquals(LocalTime.of(12, 0), result.getOpenTime());
        assertEquals(LocalTime.of(22, 0), result.getCloseTime());
        assertEquals(20000, result.getMinimumOrder());
    }



    //findAllStore 테스트
    @Test
    public void 모든_가게를_본다() {
        // given
        Long userId = 1L;
        UserEntity mockUser = new UserEntity(
                "test@naver.com",
                "Test1234!23",
                "010-1234-5678",
                "사장닉네임",
                "사장명",
                "서울시 강남구",
                Usertype.OWNER
        );
        List<StoreResponseDto> responseDtos = new ArrayList<>();
        responseDtos.add(new StoreResponseDto(
                1L,
                "국밥맛집",
                LocalTime.of(9, 0, 0),
                LocalTime.of(21, 0, 0),
                15000
        ));
        List<StoreEntity> storeEntitys = new ArrayList<>();
        for (StoreResponseDto responseDto : responseDtos) {
            storeEntitys.add(new StoreEntity(
                    responseDto.getStoreTitle(),
                    responseDto.getOpenTime(),
                    responseDto.getCloseTime(),
                    responseDto.getMinimumOrder(),
                    mockUser
                    )
            );
        }
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("LOGIN_USER", userId);

        given(storeRepository.findAll()).willReturn(storeEntitys);

        // when
        List<StoreResponseDto> result = storeService.findAllStore(userId);

        // then
        assertEquals(responseDtos.size(), result.size());
        assertEquals(responseDtos.get(0).getStoreTitle(), result.get(0).getStoreTitle());
        assertEquals(responseDtos.get(0).getMinimumOrder(), result.get(0).getMinimumOrder());
    }



    //findOneStore 테스트
    //메뉴 추가 완료
    @Test
    public void 가게와_메뉴를_확인한다() {
        // given
        Long userId = 1L;
        Long menuId = 1L;
        Long storeId = 1L;
        MenuResponseDto menuResponseDto = new MenuResponseDto(
                menuId,
                "햄버거",
                "10",
                10000,
                "img.jpg",
                MenuType.ACTIVE,
                storeId
        );
        List<MenuResponseDto> menuList = new ArrayList<>();
        menuList.add(menuResponseDto);
        OneStoreResponseDto responseDto = new OneStoreResponseDto(
                1L,
                "국밥맛집",
                LocalTime.of(9, 0, 0),
                LocalTime.of(21, 0, 0),
                15000,
                menuList
        );
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("LOGIN_USER", userId);

        given(storeService.findOneStore(userId, storeId)).willReturn(responseDto);

        // when
        OneStoreResponseDto result = storeService.findOneStore(userId, storeId);

        // then
        assertEquals("국밥맛집", result.getStoreTitle());
        assertEquals(15000, result.getMinimumOrder());
        assertEquals("햄버거", result.getMenuList().get(0).getMenuName());
        assertEquals(10000, result.getMenuList().get(0).getMenuPrice());
    }



    //deleteStore 테스트
    @Test
    public void 가게를_폐업한다() {
        // given
        Long userId = 1L;
        Long storeId = 1L;
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("LOGIN_USER", userId);

        given(storeRepository.existsById(anyLong())).willReturn(true);
        doNothing().when(storeRepository).deleteById(anyLong());

        // when
        storeService.deleteStore(userId, storeId);

        // then
        verify(storeRepository, times(1)).deleteById(storeId);//메서드 호출 여부 확인
    }



    //"가게생성에_성공한다"에 사용
    private StoreEntity makeStoreEntity(UserEntity user) {
        StoreEntity store = new StoreEntity();
        store.setStoreTitle("국밥맛집");
        store.setOpenTime(LocalTime.of(9, 0));
        store.setCloseTime(LocalTime.of(21, 0));
        store.setMinimumOrder(15000);
        store.setUser(user);
        return store;
    }
}