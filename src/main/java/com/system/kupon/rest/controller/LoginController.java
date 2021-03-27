package com.system.kupon.rest.controller;

import com.system.kupon.model.Token;
import com.system.kupon.ex.InvalidLoginException;
import com.system.kupon.rest.ClientSession;
import com.system.kupon.rest.UserSystem;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.system.kupon.model.Token.generateToken;

@RestController
@RequestMapping("/api/")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LoginController {

    @NonNull
    @Qualifier("tokens")
    private Map<String, ClientSession> tokensMap;
    @NonNull
    private UserSystem system;

    @PostMapping("login")
    public ResponseEntity<Token> login(@RequestHeader String email,
                                       @RequestHeader String password) throws InvalidLoginException {
        ClientSession session = system.createClientSession(email, password);
        String token = generateToken();
        tokensMap.put(token, session);
        token = String.valueOf(session.getRole()).concat(token);
        return ResponseEntity.ok(Token.builder().token(token).build());
    }

}
