package xyz.tomorrowlearncamp.outsourcing.domain.store.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.dto.response.MenuResponseDto;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.entity.MenuType;
import xyz.tomorrowlearncamp.outsourcing.domain.store.dto.*;
import xyz.tomorrowlearncamp.outsourcing.domain.store.service.StoreService;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
        Long userId = 1L;
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
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("LOGIN_USER", userId);

        given(storeService.saveStore(any(Long.class), any(SaveStoreRequestDto.class))).willReturn(responseDto);

        // when & then
        mockMvc.perform(post("/api/v1/stores")
                        .session(session) //세션
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }



    //updateStore 테스트
    @Test
    public void 가게의_정보를_수정한다() throws Exception {
        // given
        Long userId = 1L;
        UpdateStoreResponseDto responseDto = new UpdateStoreResponseDto(
                1L,
                "국밥맛집",
                LocalTime.of(9,0,0),
                LocalTime.of(21,0,0),
                15000
        );
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("LOGIN_USER", userId);

        given(storeService.updateStore(any(Long.class), any(Long.class), any(UpdateStoreRequestDto.class))).willReturn(responseDto);

        // when & then
        mockMvc.perform(put("/api/v1/stores/{storeId}", responseDto.getStoreId())
                        .session(session)
                        .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(responseDto)))
                .andExpect(status().isOk());
    }



    //findAllStore 테스트
    @Test
    public void 모든_가게를_본다() throws Exception {
        // given
        Long userId = 1L;
        List<StoreResponseDto> responseDto = new ArrayList<>();
        responseDto.add(new StoreResponseDto(
                1L,
                "국밥맛집",
                LocalTime.of(9,0,0),
                LocalTime.of(21,0,0),
                15000
        ));
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("LOGIN_USER", userId);

        given(storeService.findAllStore(any(Long.class))).willReturn(responseDto);

        // when & then
        mockMvc.perform(get("/api/v1/stores")
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1)) //id=1만 사용
                .andExpect(jsonPath("$[0].name").value("국밥맛집"))  //가게 이름
                .andExpect(jsonPath("$[0].minimumOrder").value(15000));  //최소주문금액
    }



    //findOneStore 테스트
    //메뉴 추가 완료
    @Test
    public void 가게와_메뉴를_확인한다() throws Exception {
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
            LocalTime.of(9,0,0),
            LocalTime.of(21,0,0),
            15000,
            menuList
        );

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("LOGIN_USER", userId);

        given(storeService.findOneStore(any(Long.class), any(Long.class))).willReturn(responseDto);

        // when & then
        mockMvc.perform(get("/api/v1/stores/{storeId}", responseDto.getStoreId())
                        .session(session)
                        .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("국밥맛집"))
                .andExpect(jsonPath("$.minimumOrder").value(15000))
                .andExpect(jsonPath("$.menuList[0].name").value("햄버거"))
                .andExpect(jsonPath("$.menuList[0].price").value(10000));

    }



    //deleteStore 테스트
    @Test
    public void 가게를_폐업한다() throws Exception {
        // given
        Long userId = 1L;
        Long storeId = 1L;
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("LOGIN_USER", userId);

        doNothing().when(storeService).deleteStore(any(Long.class), any(Long.class));//삭제와 같이, 출력 아무것도 안할때, 사용하는 given = doNothing

        // when & then
        mockMvc.perform(delete("/api/v1/stores/{storeId}", storeId)
                        .session(session)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        verify(storeService, times(1)).deleteStore(any(Long.class), any(Long.class));
    }
}
