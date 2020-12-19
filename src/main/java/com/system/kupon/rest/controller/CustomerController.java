package com.system.kupon.rest.controller;

import com.system.kupon.entity.*;
import com.system.kupon.rest.ClientSession;
import lombok.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
//- token isn't available through getSession() when @Autowired is active
public class CustomerController {

    @NonNull
    @Qualifier("tokens")
    private Map<String, ClientSession> tokensMap;

    private ClientSession getSession(String token) {
        return tokensMap.get(token);
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

    @GetMapping("/customer/coupons")
    public ResponseEntity<List<Coupon>> getAllCustomerCoupons(@RequestParam String token) {
        ClientSession session = getSession(token);
        if (session == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        List<Coupon> coupons = session.getCustomerService().getAllCouponsByCustomerId();
        return ResponseEntity.ok(coupons);
    }

    @PostMapping("/customer/{token}/update")
    public ResponseEntity<Customer> updateCustomer(@PathVariable String token,
                                                   @RequestBody Customer customer) {
        ClientSession session = getSession(token);
        if (session == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Customer uCustomer = session.getCustomerService().update(customer);
        return ResponseEntity.ok(uCustomer);
    }
}
