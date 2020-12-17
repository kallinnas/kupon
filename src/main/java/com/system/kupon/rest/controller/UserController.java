package com.system.kupon.rest.controller;

import com.system.kupon.entity.*;
import com.system.kupon.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<Client> login(@RequestParam String email,
                                        @RequestParam String password) {
        User user = userService.getUserByEmailAndPassword(email, password);
        if (user != null) return ResponseEntity.ok(user.getClient());
        return ResponseEntity.noContent().build();
    }
}
