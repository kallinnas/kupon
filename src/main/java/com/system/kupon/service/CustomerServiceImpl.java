package com.system.kupon.service;

import com.system.kupon.entity.*;
import com.system.kupon.db.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerServiceImpl implements CustomerService {

    @Setter
    @Getter
    private long customerId;
    private final CustomerRepository customerRepository;
    private final CouponRepository couponRepository;

    private Customer getExistCustomer() {
        return customerRepository.findExistById(customerId);
    }

    @Override
    public Customer getById(long id) {
        return customerRepository.findById(id).orElse(Customer.empty());
    }

    @Override
    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }

    @Override
    public List<Coupon> getAllCouponsByCustomerId() {
        return couponRepository.findAllByCustomerId(customerId);
    }

    @Override
    public Coupon addCouponToCart(long id) {
        Customer customer = getExistCustomer();
        Coupon coupon = couponRepository.findExistById(id);
        customer.getCart().add(coupon);
        customerRepository.save(customer);
        return coupon;
    }


}
