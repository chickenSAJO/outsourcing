package xyz.tomorrowlearncamp.outsourcing.store.service;

import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import xyz.tomorrowlearncamp.outsourcing.domain.store.dto.StoreSaveRequestDto;
import xyz.tomorrowlearncamp.outsourcing.domain.store.entity.StoreEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.store.repository.StoreRepository;
import xyz.tomorrowlearncamp.outsourcing.domain.store.service.StoreService;
import xyz.tomorrowlearncamp.outsourcing.domain.user.entity.UserEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.user.enums.Usertype;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class StoreServiceTest {

    @Mock
    private StoreRepository storeRepository;

    @InjectMocks
    private StoreService storeService;

    @Mock
    private HttpSession session;

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
        StoreSaveRequestDto storeSaveRequestDto = new StoreSaveRequestDto(
                "국밥맛집",
                "09:00:00",
                "21:00:00",
                15000,
                mockUser
        );

        given(storeRepository.existsByEmail(storeSaveRequestDto.getEmail())).willReturn(false);
        given(passwordEncoder.encode(storeSaveRequestDto.getPassword())).willReturn("encodedPassword");

        StoreEntity savedStore = makeStoreEntity();
        savedStore.setId(1L);

        given(storeRepository.save(any(StoreEntity.class))).willReturn(savedStore);

        // when
        Long storeId = storeService.saveStore(storeSaveRequestDto);

        // when & then
        assertEquals(1L, storeId);
    }
}
