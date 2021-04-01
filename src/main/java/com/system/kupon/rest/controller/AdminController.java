package com.system.kupon.rest.controller;

import com.system.kupon.model.Company;
import com.system.kupon.model.Coupon;
import com.system.kupon.model.Customer;
import com.system.kupon.ex.NoSuchCouponException;
import com.system.kupon.ex.NoSuchCustomerException;
import com.system.kupon.model.User;
import com.system.kupon.rest.ClientSession;
import com.system.kupon.service.AdminService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/")
@AllArgsConstructor(onConstructor = @__(@Autowired))
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AdminController {

    @Qualifier("tokens")
    private Map<String, ClientSession> tokensMap;

    @NonNull
    private ClientSession getSession(String token) {
        return tokensMap.get(token);
    }

    // COUPON
    @PutMapping("admin/coupons")
    public ResponseEntity<List<Coupon>> getAllCoupons(@RequestHeader String token) {
        ClientSession session = getSession(token);
        if (session == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.ok(session.getAdminService().getAllCoupons());
    }

    @GetMapping("admin/{token}/getCoupon/{id}")
    public ResponseEntity<Coupon> updateCoupon(@PathVariable String token,
                                               @PathVariable long id) {
        ClientSession session = getSession(token);
        if (session == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        AdminService service = session.getAdminService();
        return ResponseEntity.ok(service.getCouponById(id));
    }

    @PutMapping("admin/{token}/updateCoupon")
    public ResponseEntity<Coupon> updateCoupon(@PathVariable String token,
                                               @RequestBody Coupon coupon) throws NoSuchCouponException {
        ClientSession session = getSession(token);
        if (session == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        AdminService service = session.getAdminService();
        return ResponseEntity.ok(service.updateCoupon(coupon));
    }

    @DeleteMapping("admin/deleteCoupon/{id}")
    public List<Coupon> deleteCoupon(@RequestHeader String token,
                                     @PathVariable long id) {
        ClientSession session = getSession(token);
        AdminService service = session.getAdminService();
        return service.deleteCouponById(id);
    }

    // CUSTOMER
    @GetMapping("admin/{token}/getAllCustomers")
    public ResponseEntity<List<Customer>> getAllCustomers(@PathVariable String token) {
        ClientSession session = getSession(token);
        if (session == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        AdminService service = session.getAdminService();
        List<Customer> customers = service.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("admin/{token}/getCustomerById/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable String token,
                                                    @PathVariable long id) {
        ClientSession session = getSession(token);
        if (session == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        AdminService service = session.getAdminService();
        Customer customer = service.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }

    @GetMapping("admin/{token}/getCustomerCoupons/{id}")
    public ResponseEntity<List<Coupon>> getCustomerCoupons(@PathVariable String token,
                                                           @PathVariable long id) {
        ClientSession session = getSession(token);
        if (session == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        AdminService service = session.getAdminService();
        List<Coupon> coupons = service.getCustomerCouponsById(id);
        return ResponseEntity.ok(coupons);
    }

    @PutMapping("admin/{token}/updateCustomer")
    public ResponseEntity<Customer> updateCustomer(@PathVariable String token,
                                                   @RequestBody Customer customer) throws NoSuchCustomerException {
        ClientSession session = getSession(token);
        if (session == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        AdminService service = session.getAdminService();
        return ResponseEntity.ok(service.updateCustomer(customer));
    }

    @DeleteMapping("admin/{token}/deleteCustomerById/{id}")
    public void deleteCustomerById(@PathVariable String token,
                                   @PathVariable long id) {
        ClientSession session = getSession(token);
        AdminService service = session.getAdminService();
        service.deleteCustomerById(id);
    }

    @DeleteMapping("admin/{token}/deleteCustomersCoupon/{customerId}/{couponId}")
    public void deleteCustomersCoupon(@PathVariable String token,
                                      @PathVariable long customerId,
                                      @PathVariable long couponId) {
        ClientSession session = getSession(token);
        AdminService service = session.getAdminService();
        service.deleteCustomersCoupon(customerId, couponId);
    }

    /* COMPANY */
    @PutMapping("admin/companies")
    public ResponseEntity<List<Company>> getAllCompanies(@RequestHeader String token) {
        ClientSession session = getSession(token);
        if (session == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.ok(session.getAdminService().getAllCompanies());
    }

    @GetMapping("admin/{token}/getCompanyById/{company_id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable String token, @PathVariable long company_id) {
        ClientSession session = getSession(token);
        if (session == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        AdminService service = session.getAdminService();
        Company company = service.getCompanyById(company_id);
        return ResponseEntity.ok(company);
    }

    @GetMapping("admin/{token}/getCompanyCoupons/{id}")
    public ResponseEntity<List<Coupon>> getAllCompanyCoupons(@PathVariable String token,
                                                             @PathVariable long id) {
        ClientSession session = getSession(token);
        if (session == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        AdminService service = session.getAdminService();
        List<Coupon> coupons = service.getCouponsByCompanyId(id);
        return ResponseEntity.ok(coupons);
    }

    @PutMapping("admin/{token}/updateCompany")
    public ResponseEntity<Company> updateCompany(@PathVariable String token,
                                                 @RequestBody Company company) {
        ClientSession session = getSession(token);
        if (session == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        AdminService service = session.getAdminService();
        service.updateCompany(company);   /* Don't forget to put in updated customer to the Repo!! */
        return ResponseEntity.ok(company);
    }

    @DeleteMapping("admin/deleteCompanyById/{id}")
    public ResponseEntity<List<Company>> deleteCompanyById(@RequestHeader String token,
                                                           @PathVariable long id) {
        ClientSession session = getSession(token);
        if (session == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.ok(session.getAdminService().deleteCompanyById(id));
    }

    /* USER */
    @PutMapping("admin/users")
    public ResponseEntity<List<User>> getAllCompanyUsers(@RequestHeader String token) {
        ClientSession session = getSession(token);
        if (session == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.ok(session.getAdminService().getAllCompanyUsers());
    }
}
