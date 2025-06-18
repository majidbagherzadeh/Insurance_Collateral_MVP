package com.insurance.mvp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "collaterals.reserveWithdrawResponse.description")
public class ReserveWithdrawResponse {
    @Schema(description = "collaterals.id", example = "52")
    private String id;

    @Schema(description = "collaterals.remainedAmount", example = "7890000.00")
    private BigDecimal remainedAmount;

    @Schema(description = "collaterals.amount", example = "3005000.00")
    private BigDecimal amount;

    @Schema(description = "collaterals.reserveWithdrawResponse.draftId", example = "18")
    private Integer draftId;

    @Schema(description = "collaterals.collateralId", example = "10")
    private String collateralId;
}
