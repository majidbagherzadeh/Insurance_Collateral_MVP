package com.insurance.mvp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "پاسخ عملیات آزادسازی وثیقه")
public class ReleasesResponse {
    @Schema(description = "کد رایانه", example = "52")
    private long id;

    @Schema(description = "مبلغ باقیمانده وثیقه", example = "7890000.00")
    private BigDecimal remainedAmount;

    @Schema(description = "مبلغ مورد تایید", example = "3005000.00")
    private BigDecimal amount;

    @Schema(description = "کد رایانه وثیقه", example = "10")
    private long collateralId;
}
