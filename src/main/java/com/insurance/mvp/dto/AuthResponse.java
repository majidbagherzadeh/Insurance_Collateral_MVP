package com.insurance.mvp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Schema(description = "پاسخ احراز هویت شامل توکن دسترسی")
@AllArgsConstructor
public class AuthResponse {
    @Schema(description = "توکن دسترسی تولید شده توسط سیستم", example = "eyJhbGciOi...")
    private String accessToken;
}