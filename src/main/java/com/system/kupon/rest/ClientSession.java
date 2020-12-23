package com.system.kupon.rest;

import com.system.kupon.service.*;
import lombok.Data;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Data
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ClientSession {
    private int role;
    private long userId;
    private long lastAccessedMillis;
    private CustomerServiceImpl customerService;
    private CompanyService companyService;
    private AdminService adminService;
    private UserService userService;

    public void accessed() {
        this.lastAccessedMillis = System.currentTimeMillis();
    }

}
