package com.system.kupon.rest;

import com.system.kupon.config.CustomerErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice(annotations = RestController.class)
public class CustomerControllerAdvice {

    @ExceptionHandler(UserSystem.InvalidLoginException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public CustomerErrorResponse handleUnauthorized(UserSystem.InvalidLoginException ex) {
        return CustomerErrorResponse.of(
                HttpStatus.UNAUTHORIZED,
                "Не працює! Unauthorised!",
                System.currentTimeMillis());
    }

}
