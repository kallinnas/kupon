package com.system.kupon.service;


import com.system.kupon.rest.UserSystem;

public interface UserService {

    void registerNewUser(String email, String password, int role) throws UserSystem.InvalidLoginException;
    void updateEmail(String email, String updateEmail);
    void updatePassword(String email, String password);

}
