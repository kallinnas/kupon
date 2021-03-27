package com.system.kupon.rest;

import com.system.kupon.ex.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@ControllerAdvice(annotations = RestController.class)
public class ExceptionControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handle(UserAlreadyExistException ex) {
        return response(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handle(CouponNotInStockException ex) {
        return response(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handle(NoSuchCouponException ex) {
        return response(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handle(NoSuchCustomerException ex) {
        return response(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handle(UserIsNotExistException ex) {
        return response(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ErrorResponse> response(String message, HttpStatus status) {
        ErrorResponse error = new ErrorResponse(message, LocalDateTime.now(), status.getReasonPhrase(), status.value());
        return new ResponseEntity<>(error, status);
    }

    @Getter
    @FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
    @RequiredArgsConstructor
    private static class ErrorResponse {
        String message;
        LocalDateTime timestamp;
        String statusText;
        int statusCode;
    }

}
