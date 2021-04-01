package com.system.kupon.db;

import com.system.kupon.model.Company;
import com.system.kupon.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    Company findExistById(long id);

    Company findByCouponsContains(Coupon coupon);
}
