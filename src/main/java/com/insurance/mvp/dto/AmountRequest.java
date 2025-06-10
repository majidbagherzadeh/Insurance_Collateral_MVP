package com.insurance.mvp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "collaterals.amountRequest.description")
public class AmountRequest {
    @Schema(description = "collaterals.amountRequest.amount.description", example = "10000000")
    @NotNull(message = "collaterals.amountRequest.amount.notNull.message")
    @Min(value = 1, message = "collaterals.amountRequest.amount.min.message")
    private BigDecimal amount;
}
