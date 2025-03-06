package xyz.tomorrowlearncamp.outsourcing.store.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import xyz.tomorrowlearncamp.outsourcing.domain.store.dto.SaveStoreRequestDto;
import xyz.tomorrowlearncamp.outsourcing.domain.store.dto.SaveStoreResponseDto;
import xyz.tomorrowlearncamp.outsourcing.domain.store.dto.UpdateStoreRequestDto;
import xyz.tomorrowlearncamp.outsourcing.domain.store.entity.StoreEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.store.repository.StoreRepository;
import xyz.tomorrowlearncamp.outsourcing.domain.store.service.StoreService;
import xyz.tomorrowlearncamp.outsourcing.domain.user.entity.UserEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.user.enums.Usertype;
import xyz.tomorrowlearncamp.outsourcing.domain.user.repository.UserRepository;

import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class StoreServiceTest {

    @Mock
    private StoreRepository storeRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private StoreService storeService;

    //@Mock
    //private HttpSession session;

    @Test
    public void 가게생성에_성공한다() {
        // given
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

        given(storeRepository.existsByStoreTitle(saveStoreRequestDto.getStoreTitle())).willReturn(false);//가게 이름 확인?
        given(userRepository.existsByEmail(mockUser.getEmail())).willReturn(true);//이메일을 알맞게 입력 받았는지 확인
        given(userRepository.findByEmail(mockUser.getEmail())).willReturn(Optional.of(mockUser));//이메일이 이미 있는지 확인

        StoreEntity savedStore = makeStoreEntity(saveStoreRequestDto.getUser());//가게에 유저를 연결?
        savedStore.setStoreId(1L);

        given(storeRepository.save(any(StoreEntity.class))).willAnswer(invocation -> {
            StoreEntity store = invocation.getArgument(0);
            store.setStoreId(1L);
            return store;
        });//id가 자동 생성된다고 가정

        // when
        SaveStoreResponseDto result = storeService.saveStore(saveStoreRequestDto);

        // when & then
        assertEquals(1L, result.getStoreId());
    }



    @Test
    public void 가게_업데이트에_성공한다() {
        // given
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
        UpdateStoreRequestDto updateStoreRequestDto = new UpdateStoreRequestDto(
                "국밥맛집2",
                "12:00:00",
                "22:00:00",
                20000
        );

        given(storeRepository.existsByStoreTitle(updateStoreRequestDto.getStoreTitle())).willReturn(true);//가게 이름 확인?
        given(userRepository.existsByEmail(mockUser.getEmail())).willReturn(true);//이메일을 알맞게 입력 받았는지 확인
        given(userRepository.findByEmail(mockUser.getEmail())).willReturn(Optional.of(mockUser));//이메일이 이미 있는지 확인

        given(storeRepository.save(any(StoreEntity.class))).willAnswer(invocation -> {
            StoreEntity store = invocation.getArgument(0);
            store.setStoreId(1L);
            return store;
        });//id가 자동 생성된다고 가정

        given(storeRepository.existsByStoreTitle(saveStoreRequestDto.getStoreTitle())).willReturn(true);
        StoreEntity savedStore = makeStoreEntity(saveStoreRequestDto.getUser());//가게에 유저를 연결?
        savedStore.setStoreId(1L);

        // when
        SaveStoreResponseDto result = storeService.updateStore(,,updateStoreRequestDto);

        // when & then
        assertEquals(1L, result.getStoreId());
    }



    //"가게생성에_성공한다"에 사용
    private StoreEntity makeStoreEntity(UserEntity user) {
        StoreEntity store = new StoreEntity(
                "국밥맛집",
                LocalTime.of(9,0,0),
                LocalTime.of(21,0,0),
                15000,
                user
        );
        store.setStoreId(1L);
        return store;
    }
}