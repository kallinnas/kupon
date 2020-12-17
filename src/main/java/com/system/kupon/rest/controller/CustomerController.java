package com.system.kupon.rest.controller;

import com.system.kupon.entity.Coupon;
import com.system.kupon.entity.Customer;
import com.system.kupon.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping("/api")
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("/customer/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable long id) {
        return customerService.getById(id).getId() == Customer.NO_ID ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(customerService.getById(id));
    }

    @GetMapping("/customer/{id}/coupons")
    public ResponseEntity<List<Coupon>> getAllCustomerCoupons(@PathVariable long id) {
        return customerService.getAllCouponsByCustomerId(id).isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(customerService.getAllCouponsByCustomerId(id));
    }

    @PutMapping("/customer/update")
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer) {
        return ResponseEntity.ok(customerService.update(customer));
    }
}
