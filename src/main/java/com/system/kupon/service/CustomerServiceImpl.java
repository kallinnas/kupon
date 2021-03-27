package com.system.kupon.service;

import com.system.kupon.db.CompanyRepository;
import com.system.kupon.db.CouponRepository;
import com.system.kupon.db.CustomerRepository;
import com.system.kupon.db.UserRepository;
import com.system.kupon.model.*;
import com.system.kupon.ex.CouponNotInStockException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
//@Transactional
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerServiceImpl implements CustomerService {

    @Setter
    @Getter
    private long customerId;
    private final CustomerRepository customerRepository;
    private final CouponRepository couponRepository;
    private final ApplicationContext context;
    private final UserRepository userRepository;

    /* CUSTOMER */
    @Override
    public Customer getExistCustomer() {
        return customerRepository.findExistById(customerId);
    }

    @Override
    public Customer getCustomerById(long id) {
        return customerRepository.findById(id).orElse(Customer.empty());
    }


    @Override
    public Customer updateCustomer(Customer update) {
        Customer customer = customerRepository.findExistById(customerId);
        customer.setFirstName(update.getFirstName());
        customer.setLastName(update.getLastName());
        customerRepository.save(customer);
        return customer;
    }

    @Override
    public Coupon purchaseCoupon(long id) {
        Coupon coupon = couponRepository.findExistById(id);
        Customer customer = customerRepository.findExistById(this.customerId);
        customer.addCoupon(coupon); /* sets Customer on Coupon and vice versa */
        customer.getCart().remove(coupon);
        customerRepository.save(customer);
        return coupon;
    }

    /* COUPON */
    @Override
    public Coupon getCoupon(long id) {
        return couponRepository.findExistById(id);
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
    public List<Coupon> getCouponsByCategory(int category) {
        return couponRepository.findAllByCategory(category);
    }

    @Override
    public Coupon addCouponToCart(long id) {
        Coupon coupon = couponRepository.findExistById(id);
        if (coupon.getAmount() <= 0)
            throw new CouponNotInStockException("Amount of coupons approached zero. Coupons are not in the stock.");
        coupon.setAmount(coupon.getAmount() - 1);
        couponRepository.save(coupon);
        getExistCustomer().getCart().add(coupon);
        customerRepository.save(getExistCustomer());
        return coupon;
    }

    @Override
    public Coupon removeFromCart(long id) {
        Customer customer = getExistCustomer();
        Coupon coupon = couponRepository.findExistById(id);
        fromCartBackToStock(customer, coupon);
        return coupon;
    }

    private void fromCartBackToStock(Customer customer, Coupon coupon) {
        coupon.setAmount(coupon.getAmount() + 1);
        couponRepository.save(coupon);
        customer.getCart().remove(coupon);
        customerRepository.save(customer);
    }

    public void deleteCustomerTest(long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public void deleteCustomer(long id) {
        Customer customer = getExistCustomer();

        /* Return all coupons from customer's cart back to stock */
        while (customer.getCart().iterator().hasNext()) {
            fromCartBackToStock(customer, customer.getCart().iterator().next());
        }

        /* Del all customer coupons */
//        customer.getCoupons().clear();

        /* Del customer connections with all his coupons */
        for (Coupon c : couponRepository.findAll()) {
            if (c.getCustomers().remove(customer)) {
                couponRepository.save(c);
            }
        }




//        userRepository.deleteUserByClient(customer);
//        userRepository.deleteById(id);
//        customerRepository.deleteById(id);
//        for (User user : userRepository.findAll()) {
//            if (user.getClient().equals(customer)){
//                userRepository.delete(user);
//            }
//        }

//        for (User user : userRepository.findAll()) {
//            if (user.getClient().getId() == id)
//                if (user.getClient() instanceof Customer)
//                    userRepository.delete(user);
//        }
    }

//    @Override
//    public Coupon releaseCoupon(long coupon_id) throws NoSuchCouponException {
//        Coupon coupon = getCoupon(coupon_id);
//        List<Coupon> coupons = couponRepo.findAllByCustomerId(customerId);
//        Optional<Customer> optCustomer = customerRepo.findById(customerId);
//        if (coupons.contains(coupon) && optCustomer.isPresent()) {
//            Customer customer = optCustomer.get();
//            coupon.getCustomers().remove(customer); //delete customer from customer's list of specified coupon
//            couponRepo.save(coupon);
//            coupons.remove(getCoupon(coupon_id)); //delete coupon from list coupons that belongs to customer
//            customer.setCoupons(coupons);
//            customerRepo.save(customer);
//            System.out.println("Coupon was released successfully!");
//            return coupon;
//        }
//        String message = "No such coupon to release.";
//        throw new NoSuchCouponException(message);
//    }


    /* COMPANY */
    @Override
    public Company getCompanyById(long id) {
        return context.getBean(CompanyRepository.class).findById(id).orElse(Company.empty());
    }

    @Override
    public List<Coupon> getCouponsByCompanyId(long id) {
        return couponRepository.findAllByCompanyId(id);
    }


//    @Override
//    public List<Company> getAllCompanies() {
//        return context.getBean(CompanyRepository.class).findAll();
//    }


}
