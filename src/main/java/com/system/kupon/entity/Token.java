package com.system.kupon.entity;

import lombok.*;
import java.util.UUID;

@Data
@Builder
public class Token {
    private String token = "";
    private static final int LENGTH_TOKEN = 15;

    public static String generateToken() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, Token.LENGTH_TOKEN);
    }
}
