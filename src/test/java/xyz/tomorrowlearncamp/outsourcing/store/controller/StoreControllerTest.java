package xyz.tomorrowlearncamp.outsourcing.store.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import xyz.tomorrowlearncamp.outsourcing.domain.store.controller.StoreController;
import xyz.tomorrowlearncamp.outsourcing.domain.store.dto.*;
import xyz.tomorrowlearncamp.outsourcing.domain.store.service.StoreService;
import xyz.tomorrowlearncamp.outsourcing.domain.user.entity.UserEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.user.enums.Usertype;

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
    public void saveStore() throws Exception {
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
        StoreSaveRequestDto request = new StoreSaveRequestDto(
                "국밥맛집",
                "09:00:00",
                "21:00:00",
                15000,
                mockUser
        );
        StoreSaveResponseDto responseDto = new StoreSaveResponseDto(
                1L,
                "국밥맛집",
                "09:00:00",
                "21:00:00",
                15000,
                "사장님"
        );

        given(storeService.saveStore(any(StoreSaveRequestDto.class))).willReturn(responseDto);

        // when & then
        mockMvc.perform(post("/api/v1/stores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }



    //updateStore 테스트
    @Test
    public void updateStore() throws Exception {
        // given
        StoreUpdateResponseDto responseDto = new StoreUpdateResponseDto(
                1L,
                "국밥맛집",
                "09:00:00",
                "21:00:00",
                15000
        );

        given(storeService.updateStore(any(Long.class))).willReturn(responseDto);

        // when & then
        mockMvc.perform(put("/api/v1/stores/{storeId}", responseDto.getStoreId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }



    //findAllStore 테스트
    @Test
    public void findAllStore() throws Exception {
        // given
        List<StoreResponseDto> responseDto = new ArrayList<>();
        responseDto.add(new StoreResponseDto(
                1L,
                "국밥맛집",
                "09:00:00",
                "21:00:00",
                15000
        ));

        given(storeService.findAllStore()).willReturn(responseDto);

        // when & then
        mockMvc.perform(get("/api/v1/stores")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1)) //id=1만 사용
                .andExpect(jsonPath("$[0].name").value("국밥맛집"))  //가게 이름
                .andExpect(jsonPath("$[0].minimumOrder").value(15000));  //최소주문금액
    }



    //findOneStore 테스트
    /*메뉴 부분 추가 요망*/
    @Test
    public void findOneStore() throws Exception {
        // given
        StoreOneResponseDto responseDto = new StoreOneResponseDto(
                1L,
                "국밥맛집",
                "09:00:00",
                "21:00:00",
                15000
        );

        given(storeService.findOneStore(any(Long.class))).willReturn(responseDto);

        // when & then
        mockMvc.perform(get("/api/v1/stores/{storeId}",responseDto.getStoreId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("국밥맛집"))  //가게 이름
                .andExpect(jsonPath("$.minimumOrder").value(15000));  //최소주문금액
    }



    //deleteStore 테스트
    @Test
    public void deleteStore() throws Exception {
        // given
        Long storeId = 1L;

        doNothing().when(storeService).deleteStore(any(Long.class));//삭제와 같이, 출력 아무것도 안할때, 사용하는 given = doNothing

        // when & then
        mockMvc.perform(delete("/api/v1/stores/{storeId}", storeId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
