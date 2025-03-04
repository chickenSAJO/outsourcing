package xyz.tomorrowlearncamp.outsourcing.domain.menu.exception;

public class MenuNotFoundException extends RuntimeException {
    public MenuNotFoundException() {
        super(String.valueOf(MenuErrorCode.MENU_NOT_FOUND));
    }
}
