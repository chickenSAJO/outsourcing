package xyz.tomorrowlearncamp.outsourcing.domain.store.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.tomorrowlearncamp.outsourcing.domain.store.dto.*;
import xyz.tomorrowlearncamp.outsourcing.domain.store.entity.Store;
import xyz.tomorrowlearncamp.outsourcing.domain.store.repository.StoreRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;

    //가게 생성
    @Transactional
    public StoreSaveResponseDto saveStore(StoreSaveRequestDto dto) {
        long storeCount = storeRepository.countByUserId(dto.getUser().getId());
        if (storeCount >= 3){
            throw new IllegalStateException("가게는 최대 3개만 등록 가능합니다.");
        }
        Store store = new Store(
                dto.getStoreTitle(),
                dto.getOpenTime(),
                dto.getCloseTime(),
                dto.getMinimumOrder(),
                dto.getUser()
        );
        storeRepository.save(store);
        return new StoreSaveResponseDto(
                store.getId(),
                store.getStoreTitle(),
                store.getOpenTime(),
                store.getCloseTime(),
                store.getMinimumOrder(),
                store.getUser().getName()
        );
    }

    //가게 수정
    @Transactional
    public StoreUpdateResponseDto updateStore(Long id) {
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Store id가 없습니다."));
        store.update(
                store.getStoreTitle(),
                store.getOpenTime(),
                store.getCloseTime(),
                store.getMinimumOrder()
        );
        return new StoreUpdateResponseDto(
                store.getId(),
                store.getStoreTitle(),
                store.getOpenTime(),
                store.getCloseTime(),
                store.getMinimumOrder()
        );
    }

    //가게 다건 조회
    @Transactional(readOnly = true)
    public List<StoreResponseDto> findAllStore() {
        List<Store> stores = storeRepository.findAll();
        List<StoreResponseDto> dtos = new ArrayList<>();
        for (Store store : stores) {
            dtos.add(new StoreResponseDto(
                    store.getId(),
                    store.getStoreTitle(),
                    store.getOpenTime(),
                    store.getCloseTime(),
                    store.getMinimumOrder()
                    )
            );
        }
        return dtos;
    }

    //가게 단건 조회
    /*메뉴 연결 필요*/
    @Transactional(readOnly = true)
    public StoreOneResponseDto findOneStore(Long id) {
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Store id가 없습니다."));
        return new StoreOneResponseDto(store.getId(), store.getStoreTitle(), store.getOpenTime(), store.getCloseTime(), store.getMinimumOrder());
    }

    //가게 삭제
    @Transactional
    public void deleteStore(Long id) {
        storeRepository.deleteById(id);
    }
}
