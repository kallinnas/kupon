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
    private long lastAccessedMillis;
    private CustomerService customerService;
    private CompanyService companyService;
    private AdminService adminService;

    public void accessed() {
        this.lastAccessedMillis = System.currentTimeMillis();
    }

}
