package com.system.kupon.db;

import com.system.kupon.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {

    @Query("select coupon from Customer as customer join customer.coupons as coupon where customer.id=:id")
    List<Coupon> findAllByCustomerId(long id);

    @Query("select coupon from Company as company join company.coupons as coupon where company.id=:id")
    List<Coupon> findAllByCompanyId(long id);

    List<Coupon> findAllByTitle(String title);

    Coupon findExistById(long id);

    List<Coupon> findAllByCategory(int category);
}
