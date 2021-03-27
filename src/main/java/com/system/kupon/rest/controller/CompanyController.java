package com.system.kupon.rest.controller;

import com.system.kupon.model.Company;
import com.system.kupon.model.Coupon;
import com.system.kupon.ex.NoSuchCompanyException;
import com.system.kupon.ex.NoSuchCouponException;
import com.system.kupon.rest.ClientSession;
import com.system.kupon.service.CompanyService;
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
public class CompanyController {

    @NonNull
    @Qualifier("tokens")
    private Map<String, ClientSession> tokensMap;

    private ClientSession getSession(String token) {
        return tokensMap.get(token);
    }

    /* COMPANY */
    @PostMapping("company/account")
    public ResponseEntity<Company> getCompany(@RequestHeader String token) {
        ClientSession session = getSession(token);
        if (session == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.ok(session.getCompanyService().getCompany());
    }

    // UPDATE
    @PutMapping("company/updateCompany")
    public ResponseEntity<Company> updateCompany(@RequestHeader String token,
                                                 @RequestBody Company company) {
        ClientSession session = getSession(token);
        if (session == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        session.getCompanyService().updateCompany(company);
        return ResponseEntity.ok(company);
    }

    @PutMapping("company/updateEmail")
    public ResponseEntity<String> updateCompanyEmail(@RequestHeader String token,
                                                     @RequestHeader String email) {
        ClientSession session = getSession(token);
        if (session == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.ok(session.getCompanyService().updateCompanyEmail(email));
    }

    @PutMapping("company/updatePassword")
    public ResponseEntity<String> updateCompanyPassword(@RequestHeader String token,
                                                        @RequestHeader String newPassword) {
        ClientSession session = getSession(token);
        if (session == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.ok(session.getCompanyService().updateCompanyPassword(newPassword));
    }


    @PutMapping("company/confirmPassword")
    public ResponseEntity<Boolean> confirmPassword(@RequestHeader String token,
                                                   @RequestHeader String password) {
        ClientSession session = getSession(token);
        if (session == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.ok(session.getCompanyService().confirmPassword(password));
    }

    /* COUPONS */
    @PostMapping("company/coupons")
    public ResponseEntity<List<Coupon>> getCompanyCoupons(@RequestHeader String token) {
        ClientSession session = getSession(token);
        if (session == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.ok(session.getCompanyService().getCompanyCoupons());
    }

    @GetMapping("company/{token}/getCompanyById/{companyId}")
    public ResponseEntity<Company> getCompanyById(@PathVariable String token,
                                                  @PathVariable long companyId) {
        ClientSession session = getSession(token);
        if (session == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        CompanyService service = session.getCompanyService();
        Company company = service.getCompanyById(companyId);
        return ResponseEntity.ok(company);
    }

    @GetMapping("company/{token}/getAllCompanies")
    public ResponseEntity<List<Company>> getAllCompanies(@PathVariable String token) {
        ClientSession session = getSession(token);
        if (session == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        CompanyService service = session.getCompanyService();
        List<Company> companies = service.getAllCompanies();
        return ResponseEntity.ok(companies);
    }


    // COUPON
    @GetMapping("company/{token}/getCoupon/{id}")
    public ResponseEntity<Coupon> getCoupon(@PathVariable String token,
                                            @PathVariable long id) {
        ClientSession session = getSession(token);
        if (session == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        CompanyService service = session.getCompanyService();
        return ResponseEntity.ok(service.getCouponById(id));
    }

    @PutMapping("company/coupons/{id}")
    public ResponseEntity<List<Coupon>> getCouponsByCompanyId(@RequestHeader String token,
                                                        @PathVariable long id) {
        ClientSession session = getSession(token);
        if (session == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        CompanyService service = session.getCompanyService();
        return ResponseEntity.ok(service.getCouponsByCompanyId(id));
    }

    @GetMapping("company/{token}/getAllCoupons")
    public ResponseEntity<List<Coupon>> getAllCoupons(@PathVariable String token) {
        ClientSession session = getSession(token);
        if (session == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        CompanyService service = session.getCompanyService();
        List<Coupon> coupons = service.getAllCoupons();
        return ResponseEntity.ok(coupons);
    }


    // UPDATE FOR COUPON
    @PostMapping("company/{token}/createCoupon")
    public ResponseEntity<Coupon> createCoupon(
            @PathVariable String token,
            @RequestBody Coupon newCoupon) {
        ClientSession session = getSession(token);
        if (session == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        CompanyService service = session.getCompanyService();
        Coupon coupon = service.createCoupon(newCoupon);
        return ResponseEntity.ok(coupon);
    }

    @PutMapping("company/{token}/updateCoupon")
    public ResponseEntity<Coupon> updateCoupon(
            @PathVariable String token,
            @RequestBody Coupon update) throws NoSuchCouponException {
        ClientSession session = getSession(token);
        if (session == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        CompanyService service = session.getCompanyService();
        Coupon coupon = service.updateCoupon(update);
        return ResponseEntity.ok(coupon);
    }

    @DeleteMapping("company/{token}/deleteCouponById/{id}")
    public ResponseEntity<String> deleteCouponById(
            @PathVariable String token,
            @PathVariable long id)
            throws NoSuchCouponException, NoSuchCompanyException {
        ClientSession session = getSession(token);
        CompanyService service = session.getCompanyService();
        String msg = service.deleteCouponById(id);
        return ResponseEntity.ok(msg);
    }
}
