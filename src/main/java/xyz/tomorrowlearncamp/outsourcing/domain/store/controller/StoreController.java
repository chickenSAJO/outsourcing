package xyz.tomorrowlearncamp.outsourcing.domain.store.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.tomorrowlearncamp.outsourcing.domain.store.dto.*;
import xyz.tomorrowlearncamp.outsourcing.domain.store.service.StoreService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;

    //가게 생성
    @PostMapping("/api/v1/stores")
    public ResponseEntity<StoreSaveResponseDto> saveStore(@Valid @RequestBody StoreSaveRequestDto dto, HttpSession session){
        Long loginStore = (Long) session.getAttribute("LOGIN_OWNER");
        return ResponseEntity.ok(storeService.saveStore(dto));
    }

    //가게 수정
    @PutMapping("/api/v1/stores/{storeId}")
    public ResponseEntity<StoreUpdateResponseDto> updateStore(@PathVariable Long storeId, HttpSession session){
        Long loginStore = (Long) session.getAttribute("LOGIN_OWNER");
        return ResponseEntity.ok(storeService.updateStore(storeId));
    }

    //가게 다건 조회
    @GetMapping("/api/v1/stores")
    public ResponseEntity<List<StoreResponseDto>> findAllStore(){
        return ResponseEntity.ok(storeService.findAllStore());
    }

    //가게 단건 조회
    /*메뉴 연결 필요*/
    @GetMapping("/api/v1/stores/{storeId}")
    public ResponseEntity<StoreOneResponseDto> findOneStore(@PathVariable Long storeId){
        return ResponseEntity.ok(storeService.findOneStore(storeId));
    }

    //가게 삭제
    @DeleteMapping("/api/v1/stores/{storeId}")
    public void deleteStore(@PathVariable Long storeId, HttpSession session){
        Long loginStore = (Long) session.getAttribute("LOGIN_OWNER");
        storeService.deleteStore(storeId);
    }
}
