package com.system.kupon.service;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CompanyServiceImpl implements CompanyService {

    private long company_id;
    private final ApplicationContext context;

    @Override
    public void setCompanyId(long id) {
        this.company_id = id;
    }

}
