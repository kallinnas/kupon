package com.system.kupon.service;

import com.system.kupon.entity.*;
import com.system.kupon.repository.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerServiceImpl implements CustomerService {

    @Setter
    private long customerId;
    private final CustomerRepository customerRepository;
    private final CouponRepository couponRepository;

    @Override
    public Customer getById(long id) {
        return customerRepository.findById(id).orElse(Customer.empty());
    }

    @Override
    public Customer update(Customer customer) {

        customer.setId(customerId);
        return customerRepository.save(customer);
    }

    @Override
    public List<Coupon> getAllCouponsByCustomerId() {
        return couponRepository.findAllByCustomerId(customerId);
    }



}
