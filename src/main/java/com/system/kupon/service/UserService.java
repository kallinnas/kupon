package com.system.kupon.service;

import com.system.kupon.ex.InvalidLoginException;
import com.system.kupon.model.Coupon;

public interface UserService {

    void registerNewUser(String email, String password, int role) throws InvalidLoginException;
    void updateEmail(String email, String updateEmail);
    void updatePassword(String email, String password);
    Coupon getCouponById(long id);
}
