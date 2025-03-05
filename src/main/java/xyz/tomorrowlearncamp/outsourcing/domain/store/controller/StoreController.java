package xyz.tomorrowlearncamp.outsourcing.domain.store.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.tomorrowlearncamp.outsourcing.domain.store.dto.*;
import xyz.tomorrowlearncamp.outsourcing.domain.store.service.StoreService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stores")
public class StoreController {
    private final StoreService storeService;

    //가게 생성
    @PostMapping("")
    public ResponseEntity<SaveStoreResponseDto> saveStore(@SessionAttribute(name = "LOGIN_USER") Long userId, @Valid @RequestBody SaveStoreRequestDto dto){
        return ResponseEntity.ok(storeService.saveStore(userId, dto));
    }

    //가게 수정
    @PutMapping("/{storeId}")
    public ResponseEntity<UpdateStoreResponseDto> updateStore(@SessionAttribute(name = "LOGIN_USER") Long userId, @NotNull @Positive @PathVariable Long storeId){
        return ResponseEntity.ok(storeService.updateStore(userId, storeId));
    }

    //가게 다건 조회
    @GetMapping("")
    public ResponseEntity<List<StoreResponseDto>> findAllStore(@SessionAttribute(name = "LOGIN_USER") Long userId){
        return ResponseEntity.ok(storeService.findAllStore(userId));
    }

    //가게 단건 조회
    /*todo:메뉴 연결 필요*/
    @GetMapping("/{storeId}")
    public ResponseEntity<OneStoreResponseDto> findOneStore(@SessionAttribute(name = "LOGIN_USER") Long userId, @NotNull @Positive @PathVariable Long storeId){
        return ResponseEntity.ok(storeService.findOneStore(userId, storeId));
    }

    //가게 삭제
    @DeleteMapping("/{storeId}")
    public void deleteStore(@SessionAttribute(name = "LOGIN_USER") Long userId, @NotNull @Positive @PathVariable Long storeId){
        storeService.deleteStore(userId, storeId);
    }
}
