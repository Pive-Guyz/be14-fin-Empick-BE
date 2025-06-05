package com.piveguyz.empickbackend.common.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.piveguyz.empickbackend.common.response.CustomApiResponse;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@Hidden
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * ApiResponse 기반 커스텀 예외 핸들러
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<CustomApiResponse<Void>> handleBusinessException(BusinessException ex) {
        return ResponseEntity
                .status(ex.getCode().getHttpStatus())
                .body(CustomApiResponse.of(ex.getCode()));
    }

    /**
     * @Valid 유효성 검증 실패 처리
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomApiResponse<String>> handleValidationException(MethodArgumentNotValidException ex) {
        String errorMessage = Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage();
        return ResponseEntity.badRequest()
                .body(CustomApiResponse.of(ResponseCode.VALIDATION_FAIL, errorMessage));
    }

    /**
     * @Validated 유효성 검증 실패 처리 (예: @RequestParam)
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<CustomApiResponse<String>> handleConstraintViolation(ConstraintViolationException ex) {
        return ResponseEntity.badRequest()
                .body(CustomApiResponse.of(ResponseCode.VALIDATION_FAIL, ex.getMessage()));
    }

    /**
     * Enum 파싱 실패 처리
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<CustomApiResponse<Void>> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        Throwable cause = ex.getCause();

        if (cause instanceof InvalidFormatException invalidFormat) {
            String field = invalidFormat.getPath().get(0).getFieldName();

            if ("type".equals(field)) {
                return ResponseEntity
                        .status(ResponseCode.EMPLOYMENT_INVALID_TYPE.getHttpStatus())
                        .body(CustomApiResponse.of(ResponseCode.EMPLOYMENT_INVALID_TYPE));
            } else if ("difficulty".equals(field)) {
                return ResponseEntity
                        .status(ResponseCode.EMPLOYMENT_INVALID_DIFFICULTY.getHttpStatus())
                        .body(CustomApiResponse.of(ResponseCode.EMPLOYMENT_INVALID_DIFFICULTY));
            }
        }

        // 기타 잘못된 JSON 구조 (예: 닫히지 않은 괄호 등)
        return ResponseEntity
                .status(ResponseCode.BAD_REQUEST.getHttpStatus())
                .body(CustomApiResponse.of(ResponseCode.BAD_REQUEST));
    }

    /**
     * 그 외 모든 예외 처리 (예기치 않은 에러)
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomApiResponse<Void>> handleException(Exception ex) {
        ex.printStackTrace(); // 로그로 남기기
        return ResponseEntity
                .status(ResponseCode.INTERNAL_SERVER_ERROR.getHttpStatus())
                .body(CustomApiResponse.of(ResponseCode.INTERNAL_SERVER_ERROR));
    }
}
