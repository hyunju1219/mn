package com.meongnyang.shop.exception;

import lombok.Getter;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Map;

public class ValidException extends RuntimeException {
    @Getter
    private List<FieldError> fieldErrors;

    public ValidException(String message, List<FieldError> fieldErrors) {
        super(message);
        this.fieldErrors = fieldErrors;
    }

    @Getter
    Map<String, String> errorMap;

    public ValidException(Map<String, String> errorMap) {
        super("유효성 검사 오류");
        this.errorMap = errorMap;
    }
}
