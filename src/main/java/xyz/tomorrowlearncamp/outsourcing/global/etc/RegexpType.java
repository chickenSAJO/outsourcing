package xyz.tomorrowlearncamp.outsourcing.global.etc;

public interface RegexpType {
    String EMAIL = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
    String PASSWORD = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[\\W_]).{8,}$";
}
