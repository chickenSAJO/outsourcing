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
import xyz.tomorrowlearncamp.outsourcing.domain.store.dto.StoreSaveRequestDto;
import xyz.tomorrowlearncamp.outsourcing.domain.store.dto.StoreSaveResponseDto;
import xyz.tomorrowlearncamp.outsourcing.domain.store.service.StoreService;
import xyz.tomorrowlearncamp.outsourcing.domain.user.entity.User;
import xyz.tomorrowlearncamp.outsourcing.domain.user.enums.Usertype;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
}
