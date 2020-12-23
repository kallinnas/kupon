package com.system.kupon.service;

import com.system.kupon.db.UserRepository;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class AdminServiceImpl implements AdminService {
    private UserRepository userRepository;
}
