package com.system.kupon.rest.controller;

import com.system.kupon.entity.Token;
import com.system.kupon.rest.*;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.system.kupon.entity.Token.generateToken;

@RestController
@RequestMapping("/api")
//@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LoginController {

    @NonNull
    @Qualifier("tokens")
    private Map<String, ClientSession> tokensMap;
    @NonNull
    private UserSystem system;


    @PostMapping("/login")
    public ResponseEntity<Token> login(@RequestParam String email,
                                       @RequestParam String password) throws UserSystem.InvalidLoginException {
        ClientSession session = system.createClientSession(email, password);
        String token = generateToken();
        tokensMap.put(token, session);
        return ResponseEntity.ok(Token.builder().token(token).build());
    }

}
