package com.insurance.mvp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "پاسخ عملیات آزادسازی یا برداشت از اندوخته")
public class ReserveWithdrawResponse {
    @Schema(description = "شناسه عملیات", example = "52")
    private int Id;

    @Schema(description = "مبلغ باقیمانده بعد از عملیات", example = "7890000.00")
    private BigDecimal RemainedAmount;

    @Schema(description = "مبلغ مورد تایید عملیات", example = "3005000.00")
    private BigDecimal Amount;

    @Schema(description = "کد رایانه وثیقه", example = "10")
    private int CollateralId;

    @Schema(description = "کد رایانه حواله مرتبط (در صورت وجود)", example = "18")
    private Integer DraftId;
}
