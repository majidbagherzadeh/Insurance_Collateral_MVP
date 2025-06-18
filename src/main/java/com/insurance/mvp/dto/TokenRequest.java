package com.insurance.mvp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "collaterals.tokenRequest.description", example = "{\n  \"token\": \"eyJhbGciOi...\"\n}")
public class TokenRequest {
    @Schema(description = "collaterals.tokenRequest.token", example = "eyJhbGciOi...")
    @NotBlank
    private String token;
}
