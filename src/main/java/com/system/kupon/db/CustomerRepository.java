package com.system.kupon.db;

import com.system.kupon.model.Coupon;
import com.system.kupon.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findExistById(long id);

    @Query("SELECT customer FROM Customer AS customer JOIN customer.coupons AS coupon WHERE coupon.company.id=:id")
    Set<Customer> findByCompanyIdInCoupons(long id);

    @Query("SELECT customer FROM Customer AS customer JOIN customer.cart AS coupon WHERE coupon.company.id=:id")
    Set<Customer> findByCompanyIdInCart(long id);

    @Query("SELECT customer FROM Customer AS customer JOIN customer.coupons AS coupon WHERE coupon.id=:id")
    Set<Customer> findAllByCouponIdInCustomerCoupons(long id);

    @Query("SELECT customer FROM Customer AS customer JOIN customer.cart AS coupon WHERE coupon.id=:id")
    Set<Customer> findAllByCouponIdInCustomerCart(long id);
}
