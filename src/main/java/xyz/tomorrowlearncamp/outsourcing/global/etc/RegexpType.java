package xyz.tomorrowlearncamp.outsourcing.global.etc;

public interface RegexpType {
    String EMAIL = "^(?:[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*\n" +
            "@(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,})$\n";
    String PASSWORD = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[\\W_]).{8,}$";
    String TIME = "^(?:[01]\\d|2[0-3]):[0-5]\\d:[0-5]\\d$";
}
