package com.insurance.mvp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "پاسخ استعلام وضعیت حواله")
public class PaymentStatusResponse {
    @Schema(description = "کد رایانه حواله خسارت", example = "941487")
    private int DraftId;

    @Schema(description = "کد رایانه شخص گیرنده", example = "5336879")
    private int PartyId;

    @Schema(description = "تاریخ پرداخت", example = "1399/10/29")
    private String PaymentDate;

    @Schema(description = "مبلغ پرداخت شده", example = "576327396.00")
    private BigDecimal PaymentAmount;

    @Schema(description = "کد رایانه نحوه پرداخت", example = "275")
    private short PaymentTypeId;

    @Schema(description = "عنوان نحوه پرداخت", example = "واریز به حساب بانکی")
    private String PaymentTypeCaption;

    @Schema(description = "تاریخ تسویه نهایی", example = "1400/01/10")
    private String SettlementDate;

    @Schema(description = "مبلغ تسویه‌شده", example = "3320468.00")
    private BigDecimal SettlementAmount;

    @Schema(description = "کد رایانه نحوه تسویه", example = "17")
    private byte SettlementTypeId;

    @Schema(description = "عنوان نحوه تسویه", example = "پرداخت نقدی")
    private String SettlementTypeCaption;

    @Schema(description = "مبلغ در حال پرداخت", example = "4639560.00")
    private BigDecimal AmountBeingPaid;
}
