package com.system.kupon.service;

import com.system.kupon.entity.Company;

import java.util.List;

public interface CompanyService {
    List<Company> getAllCompanies();

    void setCompanyId(long id);

}
