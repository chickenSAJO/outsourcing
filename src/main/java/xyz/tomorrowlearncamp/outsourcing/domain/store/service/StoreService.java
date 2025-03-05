package xyz.tomorrowlearncamp.outsourcing.domain.store.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.entity.MenuEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.store.dto.*;
import xyz.tomorrowlearncamp.outsourcing.domain.store.entity.StoreEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.store.enums.StoreErrorMessage;
import xyz.tomorrowlearncamp.outsourcing.domain.store.repository.StoreRepository;
import xyz.tomorrowlearncamp.outsourcing.domain.user.entity.UserEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.user.service.UserService;
import xyz.tomorrowlearncamp.outsourcing.global.exception.InvalidRequestException;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    private final UserService userService;
    private final int STORE_CREATION_LIMIT = 3;

    //가게 생성
    @Transactional
    public SaveStoreResponseDto saveStore(Long userId, SaveStoreRequestDto dto) {
        if (storeRepository.countByUser_Id(userId) >= STORE_CREATION_LIMIT){
            throw new InvalidRequestException(StoreErrorMessage.STORE_THREE_OVER.getErrorMessage());
        }

        UserEntity user = userService.getUserEntity(userId);

        StoreEntity storeEntity = new StoreEntity(
                dto.getStoreTitle(),
                toLocalTime(dto.getOpenTime()),
                toLocalTime(dto.getCloseTime()),
                dto.getMinimumOrder(),
                user
        );
        storeRepository.save(storeEntity);
//        return new SaveStoreResponseDto(
//                storeEntity.getStoreId(),
//                storeEntity.getStoreTitle(),
//                storeEntity.getOpenTime(),
//                storeEntity.getCloseTime(),
//                storeEntity.getMinimumOrder(),
//                storeEntity.getUser().getName()
//        );
        return SaveStoreResponseDto.from(storeEntity);
    }

    //가게 수정
    @Transactional
    public UpdateStoreResponseDto updateStore(
            Long userId,
            Long storeId
    ) {
        if (storeRepository.countByUser_Id(userId) >= STORE_CREATION_LIMIT){
            throw new InvalidRequestException(StoreErrorMessage.STORE_THREE_OVER.getErrorMessage());
        }

        StoreEntity storeEntity = storeRepository.findById(storeId)
                .orElseThrow(() -> new InvalidRequestException(StoreErrorMessage.NOT_FOUND_STORE.getErrorMessage()));

//        if(ObjectUtils.NullSafeEquals(userId, )) {
//            throw new InvalidRequestException();
//        }

        UserEntity user = userService.getUserEntity(userId);

        storeEntity.update(
                storeEntity.getStoreTitle(),
                storeEntity.getOpenTime(),
                storeEntity.getCloseTime(),
                storeEntity.getMinimumOrder()
        );
//        return new UpdateStoreResponseDto(
//                storeEntity.getStoreId(),
//                storeEntity.getStoreTitle(),
//                storeEntity.getOpenTime(),
//                storeEntity.getCloseTime(),
//                storeEntity.getMinimumOrder()
//        );
        return UpdateStoreResponseDto.from(storeEntity);
    }

    //가게 다건 조회
    @Transactional(readOnly = true)
    public List<StoreResponseDto> findAllStore(Long userId) {
        if (storeRepository.countByUser_Id(userId) >= STORE_CREATION_LIMIT){
            throw new InvalidRequestException(StoreErrorMessage.STORE_THREE_OVER.getErrorMessage());
        }

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
    /*todo:메뉴 연결 필요*/
    @Transactional(readOnly = true)
    public OneStoreResponseDto findOneStore(Long userId, Long storeId) {
        if (storeRepository.countByUser_Id(userId) >= STORE_CREATION_LIMIT){
            throw new InvalidRequestException(StoreErrorMessage.STORE_THREE_OVER.getErrorMessage());
        }
        StoreEntity storeEntity = storeRepository.findById(storeId)
                .orElseThrow(() -> new InvalidRequestException(StoreErrorMessage.NOT_FOUND_STORE.getErrorMessage()));
        List<MenuEntity> menuList = storeRepository.findAllByStore(storeId);//todo: 처리해서 메뉴 넣어줄 예정.
//        return new OneStoreResponseDto(
//                storeEntity.getStoreId(),
//                storeEntity.getStoreTitle(),
//                storeEntity.getOpenTime(),
//                storeEntity.getCloseTime(),
//                storeEntity.getMinimumOrder(),
//                menuList
//        );
        return OneStoreResponseDto.from(storeEntity, menuList);
    }

    //가게 삭제
    @Transactional
    public void deleteStore(Long userId, Long storeId) {
        if (storeRepository.countByUser_Id(userId) >= STORE_CREATION_LIMIT){
            throw new InvalidRequestException(StoreErrorMessage.STORE_THREE_OVER.getErrorMessage());
        }
        storeRepository.deleteById(storeId);
    }

    private LocalTime toLocalTime(String time) {
        int[] str = Arrays.stream(time.split(":"))
                .mapToInt(Integer::parseInt).toArray();
        return LocalTime.of(str[0], str[1], str[2]);
    }
}
