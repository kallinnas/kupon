package com.system.kupon.service;

import com.system.kupon.entity.Coupon;
import com.system.kupon.entity.Customer;

import java.util.List;

public interface CustomerService {

    Customer getById(long id);

    List<Coupon> getAllCoupons();

    List<Coupon> getAllCouponsByCustomerId();

    Coupon addCouponToCart(long id);

}
