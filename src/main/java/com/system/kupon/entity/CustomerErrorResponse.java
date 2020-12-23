package com.system.kupon.entity;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor(staticName="of")
public class CustomerErrorResponse {
    private HttpStatus status;
    private String message;
    private long timestamp;

    /* lombok annotation @AllArgsConstructor(staticName="of")
    * generates constructor and refactors it
    * with static Factory method 'of':
    *
    private CustomerErrorResponse(HttpStatus status, String message, long timestamp) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }

    public static CustomerErrorResponse of(HttpStatus status, String message, long timestamp) {
        return new CustomerErrorResponse(status, message, timestamp);
    }*/
}
