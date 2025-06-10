package com.insurance.mvp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Schema(description = "collaterals.authResponse.description")
@AllArgsConstructor
public class AuthResponse {
    @Schema(description = "collaterals.authResponse.accessToken.description", example = "eyJhbGciOi...")
    private String accessToken;
}