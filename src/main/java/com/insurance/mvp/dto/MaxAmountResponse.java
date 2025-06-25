package com.insurance.mvp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "collaterals.maxAmountResponse.description")
public class MaxAmountResponse {
    @Schema(description = "collaterals.maxAmountResponse.id.description", example = "120")
    private String id;

    @Schema(description = "collaterals.nationalCode", example = "0492004986")
    private String nationalCode;

    @Schema(description = "collaterals.maxAmountResponse.ciiNumber.description", example = "80003838503")
    private String ciiNumber;

    @Schema(description = "collaterals.assigneeCompanyCode", example = "10101541927")
    private String assigneeCompanyCode;

    @Schema(description = "collaterals.maxAmount", example = "43716970.00")
    private BigDecimal maxAmount;

    @Schema(description = "collaterals.period", example = "3")
    private short period;

    @Schema(description = "collaterals.maxAmountResponse.amount.description", example = "10000000")
    private BigDecimal amount;

    @Schema(description = "collaterals.assigneeId", example = "6985014")
    private int assigneeId;
}
