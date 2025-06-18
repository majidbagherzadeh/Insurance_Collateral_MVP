package com.insurance.mvp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Schema(description = "collaterals.collateralResponse.description")
public class CollateralResponse {
    @Schema(description = "collaterals.ciiNumber", example = "80003838503")
    private String cIINumber;

    @Schema(description = "collaterals.nationalCode", example = "0492004986")
    private String nationalCode;

    @Schema(description = "collaterals.period", example = "3")
    private short period;

    @Schema(description = "collaterals.collateralResponse.id.description", example = "120")
    private String id;

    @Schema(description = "collaterals.maxAmount", example = "43716970.00")
    private BigDecimal maxAmount;

    @Schema(description = "collaterals.collateralResponse.amount.description", example = "10000000")
    private BigDecimal amount;

    @Schema(description = "collaterals.collateralResponse.remainedAmount.description", example = "3500000.00")
    private BigDecimal remainedAmount;

    @Schema(description = "collaterals.collateralResponse.beginDate.description", example = "1401/12/09")
    private String beginDate;

    @Schema(description = "collaterals.collateralResponse.endDate.description", example = "1405/07/09")
    private String endDate;

    @Schema(description = "collaterals.collateralResponse.creationTime.description", example = "2023-02-28T14:33:04")
    private LocalDateTime creationTime;

    @Schema(description = "collaterals.collateralResponse.confirmationDate.description", example = "1401/12/01")
    private String confirmationDate;

    @Schema(description = "collaterals.collateralResponse.confirmationTime.description", example = "2023-02-20T21:35:51")
    private LocalDateTime confirmationTime;

    @Schema(description = "collaterals.collateralResponse.cancellationDate.description", example = "1401/12/01")
    private String cancellationDate;

    @Schema(description = "collaterals.collateralResponse.cancellationTime.description", example = "2023-02-20T21:38:11")
    private LocalDateTime cancellationTime;

    @Schema(description = "collaterals.assigneeCompanyCode", example = "10101541927")
    private String assigneeCompanyCode;

    @Schema(description = "collaterals.assigneeId", example = "6985014")
    private int assigneeId;
}
