package xyz.tomorrowlearncamp.outsourcing.store.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.tomorrowlearncamp.outsourcing.store.dto.StoreResponseDto;
import xyz.tomorrowlearncamp.outsourcing.store.dto.StoreSaveResponseDto;
import xyz.tomorrowlearncamp.outsourcing.store.dto.StoreSaveRequestDto;
import xyz.tomorrowlearncamp.outsourcing.store.dto.StoreUpdateResponseDto;
import xyz.tomorrowlearncamp.outsourcing.store.entity.Store;
import xyz.tomorrowlearncamp.outsourcing.store.repository.StoreRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;

    //가게 생성
    @Transactional
    public StoreSaveResponseDto saveStore(StoreSaveRequestDto dto) {
        Store store = new Store(
                dto.getStoreTitle(),
                dto.getOpenTime(),
                dto.getCloseTime(),
                dto.getMinimumOrder()
        );
        storeRepository.save(store);
        return new StoreSaveResponseDto(
                store.getId(),
                store.getStoreTitle(),
                store.getOpenTime(),
                store.getCloseTime(),
                store.getMinimumOrder()
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
    public StoreResponseDto findOneStore(Long id) {
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Store id가 없습니다."));
        return new StoreResponseDto(store.getId(), store.getStoreTitle(), store.getOpenTime(), store.getCloseTime(), store.getMinimumOrder());
    }

    //가게 삭제
    @Transactional
    public void deleteStore(Long id) {
        storeRepository.deleteById(id);
    }
}
