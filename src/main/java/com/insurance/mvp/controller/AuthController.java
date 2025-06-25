package com.insurance.mvp.controller;

import com.insurance.mvp.dto.AuthResponse;
import com.insurance.mvp.dto.TokenRequest;
import com.insurance.mvp.security.TokenManager;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final TokenManager tokenManager;

    public AuthController(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    @PostMapping
    @Operation(summary = "collaterals.auth.summary", description = "collaterals.auth.description")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody TokenRequest request) {
        if (tokenManager.isValidStaticToken(request.getToken())) {
            String generatedToken = UUID.randomUUID().toString();
            tokenManager.storeIssuedToken(generatedToken);
            return ResponseEntity.ok(new AuthResponse(generatedToken));
        } else {
            return ResponseEntity.status(403).build();
        }
    }
}
