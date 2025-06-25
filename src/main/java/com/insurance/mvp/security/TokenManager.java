package com.insurance.mvp.security;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class TokenManager {
    private final TokenProperties tokenProperties;
    private final Set<String> issuedTokens = new HashSet<>();

    public TokenManager(TokenProperties tokenProperties) {
        this.tokenProperties = tokenProperties;
    }

    public boolean isValidStaticToken(String token) {
        return tokenProperties.getTokens().contains(token);
    }

    public void storeIssuedToken(String token) {
        issuedTokens.add(token);
    }

    public boolean isIssuedToken(String token) {
        return issuedTokens.contains(token);
    }
}
