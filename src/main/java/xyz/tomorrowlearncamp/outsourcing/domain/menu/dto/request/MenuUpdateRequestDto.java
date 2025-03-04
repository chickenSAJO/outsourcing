package xyz.tomorrowlearncamp.outsourcing.domain.menu.dto.request;

import lombok.Getter;

@Getter
public class MenuUpdateRequestDto {
    private String menuName;
    private String menuContent;
    private int price;
    private String menuImageUrl;
    private String menuStatus;
}
