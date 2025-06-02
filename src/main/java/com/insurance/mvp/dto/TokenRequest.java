package com.insurance.mvp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "درخواست دریافت توکن احراز هویت")
public class TokenRequest {
    @Schema(description = "توکن ارسال‌شده توسط شرکت بیمه برای احراز هویت", example = "eyJhbGciOi...")
    @NotBlank
    private String token;
}
