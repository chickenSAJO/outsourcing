package xyz.tomorrowlearncamp.outsourcing.global.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
public class ErrorResponse {

    private String status;

    private Integer code;

    private String message;

    public ErrorResponse(String statusName, Integer code, String message) {
        this.status = statusName;
        this.code = code;
        this.message = message;
    }
}
