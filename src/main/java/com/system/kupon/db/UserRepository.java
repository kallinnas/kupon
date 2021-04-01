package com.system.kupon.db;

import com.system.kupon.model.Client;
import com.system.kupon.model.Company;
import com.system.kupon.model.Coupon;
import com.system.kupon.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailAndPassword(String email, String password);

    Optional<User> findByEmail(String email);

    Optional<User> findByPassword(String password);

    User findExistByEmail(String email);

//    @Query("SELECT customer FROM Customer AS customer JOIN customer.cart AS coupon WHERE coupon.company.id=:id")
//    Set<Customer> findByCompanyIdInCart(long id);


//    @Query(value = "SELECT user FROM User AS user JOIN user.client AS client WHERE client.id=:id")
//    User findByClientId(long id);
}
