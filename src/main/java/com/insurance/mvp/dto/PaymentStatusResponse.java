package com.insurance.mvp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "collaterals.paymentStatusResponse.description")
public class PaymentStatusResponse {
    @Schema(description = "collaterals.paymentStatusResponse.draftId", example = "941487")
    private int draftId;

    @Schema(description = "collaterals.paymentStatusResponse.partyId", example = "5336879")
    private int partyId;

    @Schema(description = "collaterals.paymentStatusResponse.paymentDate", example = "1399/10/29")
    private String paymentDate;

    @Schema(description = "collaterals.paymentStatusResponse.paymentAmount", example = "576327396.00")
    private BigDecimal paymentAmount;

    @Schema(description = "collaterals.paymentStatusResponse.paymentTypeId", example = "275")
    private short paymentTypeId;

    @Schema(description = "collaterals.paymentStatusResponse.paymentTypeCaption", example = "collaterals.paymentStatusResponse.paymentTypeCaption.example")
    private String paymentTypeCaption;

    @Schema(description = "collaterals.paymentStatusResponse.settlementDate", example = "1400/01/10")
    private String settlementDate;

    @Schema(description = "collaterals.paymentStatusResponse.settlementAmount", example = "3320468.00")
    private BigDecimal settlementAmount;

    @Schema(description = "collaterals.paymentStatusResponse.settlementTypeId", example = "17")
    private byte settlementTypeId;

    @Schema(description = "collaterals.paymentStatusResponse.settlementTypeCaption", example = "پرداخت نقدی")
    private String settlementTypeCaption;

    @Schema(description = "collaterals.paymentStatusResponse.amountBeingPaid", example = "4639560.00")
    private BigDecimal amountBeingPaid;
}
