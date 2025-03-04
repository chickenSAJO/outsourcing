package xyz.tomorrowlearncamp.outsourcing.domain.store.enums;

public enum StoreTimeType {
    TIME_FORMAT("^(?:[01]\\d|2[0-3]):[0-5]\\d:[0-5]\\d$");

    public static final String TIME_FORMAT_PATTERN = "^(?:[01]\\d|2[0-3]):[0-5]\\d:[0-5]\\d$"; //00:00:00~23:59:59

    private final String pattern;

    StoreTimeType(String pattern) {
        this.pattern = pattern;
    }

    public String getPattern() {
        return pattern;
    }
}
