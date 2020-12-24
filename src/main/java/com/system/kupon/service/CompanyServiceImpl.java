package com.system.kupon.service;

import com.system.kupon.db.CompanyRepository;
import com.system.kupon.entity.Company;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
//@Transactional
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CompanyServiceImpl implements CompanyService {

    @Setter
    private long companyId;
    private final CompanyRepository companyRepository;
    private final ApplicationContext context;


    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }
}
