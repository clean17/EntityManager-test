package shop.mtcoding.hiberpc.config.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class MyException extends RuntimeException {
    private HttpStatus httpStatus;

    public MyException(String message) {
        this(message, HttpStatus.BAD_REQUEST);
    }

    public MyException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}