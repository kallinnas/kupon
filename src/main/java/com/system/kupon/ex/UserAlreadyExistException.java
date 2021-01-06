package com.system.kupon.ex;

public class UserAlreadyExistException extends AbstractAuthenticationException {
    public UserAlreadyExistException(String msg) {
        super(msg);
    }
}
