package com.system.kupon.rest.controller;

import com.system.kupon.rest.*;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LoginController {

    @NonNull
    @Qualifier("tokens")
    private Map<String, ClientSession> tokensMap;
    @NonNull
    private UserSystem system;


    @PostMapping("/login")
    public ResponseEntity<Token> login(@RequestParam String email,
                                       @RequestParam String password) {
        /* @RequestParam - using this @ arguments will be hidden and won't appear in URI */
        ClientSession session = system.login(email, password);
        String token = new Token().generateToken();
        tokensMap.put(token, session);
        Token myToken = new Token();
        myToken.setToken(token);
        return ResponseEntity.ok(myToken);
    }

    @Data
    public static class Token {
        private String token = "";
        private static final int LENGTH_TOKEN = 15;

        String generateToken() {
            return UUID.randomUUID().toString().replaceAll("-", "").substring(0, Token.LENGTH_TOKEN);
        }
    }


}
