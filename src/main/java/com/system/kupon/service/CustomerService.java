package com.system.kupon.service;

import com.system.kupon.model.Company;
import com.system.kupon.model.Coupon;
import com.system.kupon.model.Customer;

import java.util.List;

public interface CustomerService {

    /* CUSTOMER */
    Customer getExistCustomer();
    Customer getCustomerById(long id);
    Customer updateCustomer(Customer update);
    void deleteCustomer(long id);
    void deleteCustomerTest(long userId);


    /* COUPON */
    Coupon getCoupon(long id);
    List<Coupon> getAllCoupons();
    List<Coupon> getAllCouponsByCustomerId();
    List<Coupon> getCouponsByCategory(int category);
    Coupon addCouponToCart(long id);


    /* COMPANY */
    Company getCompanyById(long id);
    List<Coupon> getCouponsByCompanyId(long id);
    Coupon removeFromCart(long id);
    Coupon purchaseCoupon(long id);

}
