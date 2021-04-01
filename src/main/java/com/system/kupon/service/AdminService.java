package com.system.kupon.service;

import com.system.kupon.model.*;
import com.system.kupon.ex.NoSuchCouponException;
import com.system.kupon.ex.NoSuchCustomerException;
import com.system.kupon.ex.UserIsNotExistException;

import java.util.List;

public interface AdminService {

    /* Coupon */
    List<Coupon> getAllCoupons();

    Coupon updateCoupon(Coupon coupon) throws NoSuchCouponException;

    List<Coupon> deleteCouponById(long id);

    /* Customer */
    Customer getCustomerById(long id);

    List<Customer> getAllCustomers();

    List<Coupon> getCustomerCouponsById(long customer_id);

    Customer updateCustomer(Customer customer) throws NoSuchCustomerException;

    void deleteCustomerById(long id);

    void deleteCustomersCoupon(long customerId, long couponId);

    /* Company */
    List<Company> getAllCompanies();

    Company getCompanyById(long id);

    List<Coupon> getCouponsByCompanyId(long company_id);

    void updateCompany(Company company);

    List<Company> deleteCompanyById(long id);

    Coupon getCouponById(long id);

    List<User> getAllCompanyUsers();
}
