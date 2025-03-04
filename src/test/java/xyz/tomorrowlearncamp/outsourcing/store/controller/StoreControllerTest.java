package xyz.tomorrowlearncamp.outsourcing.store.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import xyz.tomorrowlearncamp.outsourcing.domain.store.controller.StoreController;
import xyz.tomorrowlearncamp.outsourcing.domain.store.dto.StoreResponseDto;
import xyz.tomorrowlearncamp.outsourcing.domain.store.dto.StoreSaveRequestDto;
import xyz.tomorrowlearncamp.outsourcing.domain.store.dto.StoreSaveResponseDto;
import xyz.tomorrowlearncamp.outsourcing.domain.store.dto.StoreUpdateResponseDto;
import xyz.tomorrowlearncamp.outsourcing.domain.store.service.StoreService;
import xyz.tomorrowlearncamp.outsourcing.domain.user.entity.User;
import xyz.tomorrowlearncamp.outsourcing.domain.user.enums.Usertype;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
        User mockUser = new User(
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
        mockMvc.perform(put("/api/v1/stores/1")
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
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].name").value("국밥맛집"))  //가게 이름
                .andExpect(jsonPath("$[0].price").value(15000));  //최소주문금액
    }
}
