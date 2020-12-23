package com.system.kupon.rest.controller;

import com.system.kupon.entity.*;
import com.system.kupon.rest.ClientSession;
import lombok.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerController {

    @NonNull
    @Qualifier("tokens")
    private Map<String, ClientSession> tokensMap;

    private ClientSession getSession(String token) {
        return tokensMap.get(token);
    }

    @GetMapping("/customer") // Method returns info about customer to himself
    public ResponseEntity<Customer> getCustomer(@RequestParam String token) {
        ClientSession session = getSession(token);
        if (session == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        long id = session.getCustomerService().getCustomerId();
        Customer customer = session.getCustomerService().getById(id);
        return ResponseEntity.ok(customer);
    }

    @GetMapping("/customer/coupons")
    public ResponseEntity<List<Coupon>> getAllCustomerCoupons(@RequestParam String token) {
        ClientSession session = getSession(token);
        if (session == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        List<Coupon> coupons = session.getCustomerService().getAllCouponsByCustomerId();
        return ResponseEntity.ok(coupons);
    }

    @PostMapping("/customer/addToCart/{id}")
    public ResponseEntity<String> addCouponToCart(@RequestParam String token,
                                                  @PathVariable long id) {
        ClientSession session = getSession(token);
        if (session == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        Coupon coupon = session.getCustomerService().addCouponToCart(id);
        return ResponseEntity.ok(String.format("Coupon %s was added to your cart successfully!", coupon.getTitle()));
    }



//    @GetMapping("/customer/{id}")
//    public ResponseEntity<Customer> getCustomerById(@PathVariable long id,
//                                                    @PathVariable String token) {
//        ClientSession session = getSession(token);
//        if (session == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        Customer customer = session.getCustomerService().getById(id);
//        return customer.getId() == Customer.NO_ID ?
//                ResponseEntity.noContent().build() : ResponseEntity.ok(customer);
//    }



//    @PostMapping("/customer/{token}/update")
//    public ResponseEntity<Customer> updateCustomer(@PathVariable String token,
//                                                   @RequestBody Customer customer) {
//        ClientSession session = getSession(token);
//        if (session == null){
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
//        Customer uCustomer = session.getCustomerService().update(customer);
//        return ResponseEntity.ok(uCustomer);
//    }
}
