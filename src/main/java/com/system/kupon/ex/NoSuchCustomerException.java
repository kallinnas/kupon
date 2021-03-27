package com.system.kupon.ex;

public class NoSuchCustomerException extends RuntimeException {
    public NoSuchCustomerException(String msg) {
        super(msg);
    }
}
