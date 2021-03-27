package com.system.kupon.db;

import com.system.kupon.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findByFirstName(String firstName);

    Customer findExistById(long id);

    Optional<Customer> findByLastName(String lastName);

    List<Customer> findAllCustomersByCoupons(long id);

}
