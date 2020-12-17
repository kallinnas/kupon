package com.system.kupon.service;

import com.system.kupon.entity.*;
import com.system.kupon.repository.*;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerServiceImpl implements CustomerService {
    private long customerId;
    private final CustomerRepository customerRepository;
    private final CouponRepository couponRepository;


    @Override
    public void setCustomerId(long id) {
        this.customerId = id;
    }

    @Override
    public Customer getById(long id) {
        return customerRepository.findById(id).orElse(Customer.empty());
    }

    @Override
    public Customer update(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public List<Coupon> getAllCouponsByCustomerId(long id) {
        return couponRepository.findAllByCustomerId(id);
    }



}
