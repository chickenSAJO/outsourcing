package xyz.tomorrowlearncamp.outsourcing.domain.store.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.tomorrowlearncamp.outsourcing.domain.store.dto.*;
import xyz.tomorrowlearncamp.outsourcing.domain.store.entity.StoreEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.store.enums.StoreErrorMessage;
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
        if (storeRepository.countByUserId(dto.getUser().getId()) >= 3){
            throw new IllegalStateException(StoreErrorMessage.STORE_THREE_OVER.getErrorMessage());
        }

        StoreEntity storeEntity = new StoreEntity(
                dto.getStoreTitle(),
                dto.getOpenTime(),
                dto.getCloseTime(),
                dto.getMinimumOrder(),
                dto.getUser()
        );
        storeRepository.save(storeEntity);
        return new StoreSaveResponseDto(
                storeEntity.getStoreId(),
                storeEntity.getStoreTitle(),
                storeEntity.getOpenTime(),
                storeEntity.getCloseTime(),
                storeEntity.getMinimumOrder(),
                storeEntity.getUser().getName()
        );
    }

    //가게 수정
    @Transactional
    public StoreUpdateResponseDto updateStore(Long storeId) {
        StoreEntity storeEntity = storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalStateException(StoreErrorMessage.NOT_FOUND_STORE.getErrorMessage()));
        storeEntity.update(
                storeEntity.getStoreTitle(),
                storeEntity.getOpenTime(),
                storeEntity.getCloseTime(),
                storeEntity.getMinimumOrder()
        );
        return new StoreUpdateResponseDto(
                storeEntity.getStoreId(),
                storeEntity.getStoreTitle(),
                storeEntity.getOpenTime(),
                storeEntity.getCloseTime(),
                storeEntity.getMinimumOrder()
        );
    }

    //가게 다건 조회
    @Transactional(readOnly = true)
    public List<StoreResponseDto> findAllStore() {
        List<StoreEntity> storeEntities = storeRepository.findAll();
        List<StoreResponseDto> dtos = new ArrayList<>();
        for (StoreEntity storeEntity : storeEntities) {
            dtos.add(new StoreResponseDto(
                    storeEntity.getStoreId(),
                    storeEntity.getStoreTitle(),
                    storeEntity.getOpenTime(),
                    storeEntity.getCloseTime(),
                    storeEntity.getMinimumOrder()
                    )
            );
        }
        return dtos;
    }

    //가게 단건 조회
    /*메뉴 연결 필요*/
    @Transactional(readOnly = true)
    public StoreOneResponseDto findOneStore(Long storeId) {
        StoreEntity storeEntity = storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalStateException(StoreErrorMessage.NOT_FOUND_STORE.getErrorMessage()));
        return new StoreOneResponseDto(storeEntity.getStoreId(), storeEntity.getStoreTitle(), storeEntity.getOpenTime(), storeEntity.getCloseTime(), storeEntity.getMinimumOrder());
    }

    //가게 삭제
    @Transactional
    public void deleteStore(Long storeId) {
        storeRepository.deleteById(storeId);
    }
}
