package com.system.kupon.ex;

public class InvalidLoginException extends AbstractAuthenticationException {
    public InvalidLoginException(String message) {
        super(message);
    }
}
