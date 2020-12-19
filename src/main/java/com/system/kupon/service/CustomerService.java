package com.system.kupon.service;

import com.system.kupon.entity.Coupon;
import com.system.kupon.entity.Customer;

import java.util.List;

public interface CustomerService {

    Customer getById(long id);

    Customer update(Customer customer);

    List<Coupon> getAllCouponsByCustomerId();

}
