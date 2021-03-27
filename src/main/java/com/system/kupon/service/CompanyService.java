package com.system.kupon.service;

import com.system.kupon.model.Company;
import com.system.kupon.model.Coupon;
import com.system.kupon.ex.NoSuchCompanyException;
import com.system.kupon.ex.NoSuchCouponException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CompanyService {

    Company getCompany();
    Company getCompanyById(long id);
    List<Company> getAllCompanies();
    void updateCompany(Company update);

    /* COUPON */
    Coupon getCouponById(long id);
    List<Coupon> getAllCoupons();
    List<Coupon> getCompanyCoupons();

    // UPDATE FOR COUPON
    Coupon createCoupon(Coupon coupon);
    Coupon updateCoupon(Coupon coupon) throws NoSuchCouponException;

    @Transactional
    String deleteCouponById(long id) throws NoSuchCompanyException, NoSuchCouponException;

    Boolean confirmPassword(String password);

    String updateCompanyEmail(String email);

    String updateCompanyPassword(String newPassword);

    List<Coupon> getCouponsByCompanyId(long id);
}
