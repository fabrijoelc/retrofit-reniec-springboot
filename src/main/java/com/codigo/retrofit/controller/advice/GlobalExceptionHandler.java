package com.codigo.retrofit.controller.advice;

import com.codigo.retrofit.aggregates.response.ApiErrorResponse;
import com.codigo.retrofit.exception.ConsultaReniecException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ApiErrorResponse> handleException(Throwable ex) {
        ApiErrorResponse apiErrorResponse =
                new ApiErrorResponse(ex.getClass().getSimpleName(), ex.getMessage());

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if (ex instanceof ConsultaReniecException) {
            status = HttpStatus.BAD_GATEWAY;
        }

        return ResponseEntity.status(status).body(apiErrorResponse);
    }
}
