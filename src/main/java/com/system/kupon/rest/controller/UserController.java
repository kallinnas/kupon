package com.system.kupon.rest.controller;

import com.system.kupon.db.CouponRepository;
import com.system.kupon.entity.*;
import com.system.kupon.rest.ClientSession;
import com.system.kupon.rest.UserSystem;
import com.system.kupon.service.CompanyService;
import com.system.kupon.service.CustomerService;
import com.system.kupon.service.UserService;

import lombok.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.system.kupon.entity.Token.generateToken;

@RestController
@RequestMapping("/api")
//@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    @NonNull
    @Qualifier("tokens")
    private Map<String, ClientSession> tokensMap;
    @NonNull
    private ApplicationContext context;

    private ClientSession getSession(String token) {
        return tokensMap.get(token);
    }


    @PostMapping("/user/reg")
    public ResponseEntity<Token> registration(@RequestHeader String email,
                                              @RequestHeader String password,
                                              @RequestHeader int role) {
        context.getBean(UserService.class).registerNewUser(email, password, role);
        ClientSession session = context.getBean(UserSystem.class).createClientSession(email, password);
        String token = generateToken();
        tokensMap.put(token, session);
        return ResponseEntity.ok(Token.builder().token(token).build());
    }

    @PostMapping("/user/changeEmail")
    public ResponseEntity<String> changeEmail(@RequestParam String token,
                                              @RequestParam String email,
                                              @RequestParam String updateEmail) {
        ClientSession session = getSession(token);
        if (session == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        session.getUserService().updateEmail(email, updateEmail);
        return ResponseEntity.ok(String.format("Email address was changed from %s to %s successfully!", email, updateEmail));
    }

    @PostMapping("/user/changePassword")
    public ResponseEntity<String> changePassword(@RequestParam String token,
                                                 @RequestParam String email,
                                                 @RequestParam String password) {
        ClientSession session = getSession(token);
        if (session == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        session.getUserService().updatePassword(email, password);
        return ResponseEntity.ok("The password was been changed successfully!");
    }

    @GetMapping("/getAllCompanies")
    public ResponseEntity<List<Company>> getAllCompanies() {
        List<Company> companies = context.getBean(CompanyService.class).getAllCompanies();
        return ResponseEntity.ok(companies);
    }

    @GetMapping("/getAllCoupons")
    public ResponseEntity<List<Coupon>> getAllCoupons() {
        List<Coupon> coupons = context.getBean(CustomerService.class).getAllCoupons();
        return ResponseEntity.ok(coupons);
    }
}
