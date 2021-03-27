package com.system.kupon.ex;

public class NoSuchCompanyException extends RuntimeException {
    public NoSuchCompanyException(String message) {
        super(message);
    }
}
