package com.system.kupon.ex;

public class UserIsNotExistException extends Exception {
    public UserIsNotExistException(String msg){
        super(msg);
    }
}
