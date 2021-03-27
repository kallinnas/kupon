package com.system.kupon.rest.controller;

import com.system.kupon.model.Company;
import com.system.kupon.model.Coupon;
import com.system.kupon.model.Customer;
import com.system.kupon.rest.ClientSession;
import com.system.kupon.service.CustomerService;
import com.system.kupon.service.CustomerServiceImpl;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerController {

    @NonNull
    @Qualifier("tokens")
    private Map<String, ClientSession> tokensMap;

    private ClientSession getSession(String token) {
        return tokensMap.get(token);
    }

    /* CUSTOMER */
    @PostMapping("customer/account")
    public ResponseEntity<Customer> getCustomer(@RequestHeader String token) {
        ClientSession session = getSession(token);
        if (session == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.ok(session.getCustomerService().getExistCustomer());
    }

    @PutMapping("customer/{token}/update")
    public ResponseEntity<Customer> updateCustomer(@PathVariable String token,
                                                   @RequestBody Customer customer) {
        ClientSession session = getSession(token);
        if (session == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        CustomerServiceImpl service = session.getCustomerService();
        Customer updateCustomer = service.updateCustomer(customer);
        return ResponseEntity.ok(updateCustomer);
    }

    @DeleteMapping("customer/{token}/deleteCustomerById/{id}")
    public void deleteCustomerById(@PathVariable String token, @PathVariable long id) {
        ClientSession session = getSession(token);
        CustomerService service = session.getCustomerService();
        service.deleteCustomer(id);
    }

    @DeleteMapping("customer/{token}/deleteTest/{id}")
    public void deleteTest(@PathVariable String token, @PathVariable long id) {
        ClientSession session = getSession(token);
        CustomerService service = session.getCustomerService();
        service.deleteCustomerTest(id);
    }

    /* COUPON */
    @GetMapping("customer/{token}/get/{id}")
    public ResponseEntity<Coupon> getCoupon(@PathVariable String token,
                                            @PathVariable long id) {
        ClientSession session = getSession(token);
        if (session == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.ok(session.getCustomerService().getCoupon(id));
    }

    @GetMapping("customer/{token}/getAllCoupons")
    public ResponseEntity<List<Coupon>> getAllCoupons(@PathVariable String token) {
        ClientSession session = getSession(token);
        if (session == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        CustomerService service = session.getCustomerService();
        List<Coupon> coupons = service.getAllCoupons();
        return ResponseEntity.ok(coupons);
    }

    @GetMapping("customer/coupons")
    public ResponseEntity<List<Coupon>> getAllCustomerCoupons(@RequestParam String token) {
        ClientSession session = getSession(token);
        if (session == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        List<Coupon> coupons = session.getCustomerService().getAllCouponsByCustomerId();
        return ResponseEntity.ok(coupons);
    }

    @PostMapping("customer/{token}/buyCoupon/{id}")
    public ResponseEntity<Coupon> purchaseCoupon(@PathVariable String token,
                                                 @PathVariable long id) {
        ClientSession session = getSession(token);
        if (session == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        CustomerService service = session.getCustomerService();
        return ResponseEntity.ok(service.purchaseCoupon(id));
    }

//    @GetMapping("customer/{token}/category/{category}")
//    public ResponseEntity<List<Coupon>> getCouponsByCategory(@PathVariable String token,
//                                                             @PathVariable int category) {
//        ClientSession session = getSession(token);
//        if (session == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        CustomerService service = session.getCustomerService();
//        List<Coupon> coupons = service.getCouponsByCategory(category);
//        return ResponseEntity.ok(coupons);
//    }

    /* CART */
    @PostMapping("customer/{token}/addToCart/{id}")
    public ResponseEntity<Coupon> addCouponToCart(@PathVariable String token,
                                                  @PathVariable long id) {
        ClientSession session = getSession(token);
        if (session == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        Coupon coupon = session.getCustomerService().addCouponToCart(id);
        return ResponseEntity.ok(coupon);
    }

    @PutMapping("customer/{token}/removeFromCart/{id}")
    public ResponseEntity<Coupon> removeFromCart(@PathVariable String token,
                                                 @PathVariable long id) {
        ClientSession session = getSession(token);
        if (session == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.ok(session.getCustomerService().removeFromCart(id));
    }


    /* COMPANY */
    @GetMapping("customer/{token}/getCompanyById/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable String token, @PathVariable long id) {
        ClientSession session = getSession(token);
        if (session == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        CustomerService service = session.getCustomerService();
        Company company = service.getCompanyById(id);
        return ResponseEntity.ok(company);
    }

    @GetMapping("customer/{token}/companyCoupons/{id}")
    public ResponseEntity<List<Coupon>> getCouponsByCompanyId(@PathVariable String token,
                                                              @PathVariable long id) {
        ClientSession session = getSession(token);
        if (session == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        CustomerService service = session.getCustomerService();
        List<Coupon> allCompanyCoupons = service.getCouponsByCompanyId(id);
        return ResponseEntity.ok(allCompanyCoupons);
    }

}
