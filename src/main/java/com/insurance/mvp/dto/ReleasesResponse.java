package com.insurance.mvp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "collaterals.releaseResponse.description")
public class ReleasesResponse {
    @Schema(description = "collaterals.id", example = "52")
    private String id;

    @Schema(description = "collaterals.remainedAmount", example = "7890000.00")
    private BigDecimal remainedAmount;

    @Schema(description = "collaterals.amount", example = "3005000.00")
    private BigDecimal amount;

    @Schema(description = "collaterals.collateralId", example = "10")
    private String collateralId;
}
