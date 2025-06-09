package com.insurance.mvp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "درخواست تایید، آزادسازی یا برداشت مبلغ وثیقه")
public class AmountRequest {
    @Schema(description = "مبلغ برای عملیات", example = "10000000")
    @NotNull(message = "مبلغ الزامی است")
    @Min(value = 1, message = "مبلغ باید بزرگتر از 0 باشد")
    private BigDecimal amount;
}
