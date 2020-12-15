package com.system.kupon.service;

import com.system.kupon.entity.User;

public interface UserService {
    User getUserByEmailAndPassword(String email, String password);
}
