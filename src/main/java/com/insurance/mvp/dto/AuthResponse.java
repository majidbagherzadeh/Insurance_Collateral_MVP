package com.insurance.mvp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "پاسخ احراز هویت شامل توکن دسترسی")
public class AuthResponse {
    @Schema(description = "توکن دسترسی تولید شده توسط سیستم", example = "eyJhbGciOi...")
    private String accessToken;
    public AuthResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}