package com.system.kupon.rest.controller;

import com.system.kupon.rest.*;
import lombok.*;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;


import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/api")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class LoginController {

//    @Qualifier("tokens")
    private final Map<String, ClientSession> tokensMap;
//    private final ApplicationContext context;
    private final UserSystem system;

    @Data
    public static class Token {
        private String token = "";
        private static final int LENGTH_TOKEN = 15;

        String generateToken() {
            return UUID.randomUUID().toString().replaceAll("-", "").substring(0, Token.LENGTH_TOKEN);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Token> login(@PathVariable String email,
                                       @PathVariable String password) {
        ClientSession session = system.login(email, password);
        String token = new Token().generateToken();
        tokensMap.put(token, session);
        Token myToken = new Token();
        myToken.setToken(token);
        return ResponseEntity.ok(myToken);
    }


}
