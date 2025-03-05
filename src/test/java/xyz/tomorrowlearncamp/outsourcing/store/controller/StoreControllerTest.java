package xyz.tomorrowlearncamp.outsourcing.store.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.dto.response.MenuResponseDto;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.entity.MenuEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.store.controller.StoreController;
import xyz.tomorrowlearncamp.outsourcing.domain.store.dto.*;
import xyz.tomorrowlearncamp.outsourcing.domain.store.entity.StoreEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.store.service.StoreService;
import xyz.tomorrowlearncamp.outsourcing.domain.user.entity.UserEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.user.enums.Usertype;
import xyz.tomorrowlearncamp.outsourcing.domain.user.service.UserService;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StoreController.class)
public class StoreControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private final StoreService storeService;

    public StoreControllerTest(StoreService storeService) {
        this.storeService = storeService;
    }

    //saveStore 테스트
    @Test
    public void 가게를_생성한다() throws Exception {
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
        SaveStoreRequestDto request = new SaveStoreRequestDto(
                "국밥맛집",
                "09:00:00",
                "23:00:00",
                15000
        );
        SaveStoreResponseDto responseDto = new SaveStoreResponseDto(
                1L,
                "국밥맛집",
                LocalTime.of(9,0,0),
                LocalTime.of(21,0,0),
                15000,
                "사장님"
        );

        given(storeService.saveStore(any(Long.class), any(SaveStoreRequestDto.class))).willReturn(responseDto);

        // when & then
        mockMvc.perform(post("/api/v1/stores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }



    //updateStore 테스트
    @Test
    public void 가게의_정보를_수정한다() throws Exception {
        // given
        UpdateStoreResponseDto responseDto = new UpdateStoreResponseDto(
                1L,
                "국밥맛집",
                LocalTime.of(9,0,0),
                LocalTime.of(21,0,0),
                15000
        );

        given(storeService.updateStore(any(Long.class), any(Long.class), any(UpdateStoreRequestDto.class))).willReturn(responseDto);

        // when & then
        mockMvc.perform(put("/api/v1/stores/{storeId}", responseDto.getStoreId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }



    //findAllStore 테스트
    @Test
    public void 모든_가게를_본다() throws Exception {
        // given
        List<StoreResponseDto> responseDto = new ArrayList<>();
        responseDto.add(new StoreResponseDto(
                1L,
                "국밥맛집",
                LocalTime.of(9,0,0),
                LocalTime.of(21,0,0),
                15000
        ));

        given(storeService.findAllStore(any(Long.class))).willReturn(responseDto);

        // when & then
        mockMvc.perform(get("/api/v1/stores")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1)) //id=1만 사용
                .andExpect(jsonPath("$[0].name").value("국밥맛집"))  //가게 이름
                .andExpect(jsonPath("$[0].minimumOrder").value(15000));  //최소주문금액
    }



    //findOneStore 테스트
    /*todo: 메뉴 부분 추가 요망*/
    @Test
    public void 가게와_메뉴를_확인한다() throws Exception {
        // given
        Long id = 1L;
        MenuResponseDto menuResponseDto = new MenuResponseDto(
                id,
                "햄버거",
                "10",
                10000,
                "img.jpg",
                ACTIVE
        );
        OneStoreResponseDto responseDto = new OneStoreResponseDto(
            1L,
            "국밥맛집",
            LocalTime.of(9,0,0),
            LocalTime.of(21,0,0),
            15000,
            menuResponseDto
            //todo: 메뉴 연결되면 작성할 예정
        );

        given(storeService.findOneStore(any(Long.class), any(Long.class))).willReturn(responseDto);

        // when & then
        mockMvc.perform(get("/api/v1/stores/{storeId}",responseDto.getStoreId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("국밥맛집"))  //가게 이름
                .andExpect(jsonPath("$.minimumOrder").value(15000));  //최소주문금액
    }



    //deleteStore 테스트
    @Test
    public void 가게를_폐업한다() throws Exception {
        // given
        Long storeId = 1L;

        doNothing().when(storeService).deleteStore(any(Long.class), any(Long.class));//삭제와 같이, 출력 아무것도 안할때, 사용하는 given = doNothing

        // when & then
        mockMvc.perform(delete("/api/v1/stores/{storeId}", storeId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }



    //"가게와_메뉴를_확인한다"에 사용
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
