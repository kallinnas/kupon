package com.system.kupon.rest;

import com.system.kupon.ex.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@ControllerAdvice(annotations = RestController.class)
public class ExceptionControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handle(AbstractAuthenticationException ex) {
        return response(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handle(CouponNotInStockException ex) {
        return response(ex.getMessage(), HttpStatus.CONFLICT);
    }

    private ResponseEntity<ErrorResponse> response(String message, HttpStatus status) {
        ErrorResponse error = new ErrorResponse(message, LocalDateTime.now(), status.getReasonPhrase(), status.value());
        return new ResponseEntity<>(error, status);
    }

    @Getter
    @FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
    @RequiredArgsConstructor // Generates constructor for every final or @NonNull field
    private static class ErrorResponse {
        String message;
        LocalDateTime timestamp;
        String statusText;
        int statusCode;
    }

}
