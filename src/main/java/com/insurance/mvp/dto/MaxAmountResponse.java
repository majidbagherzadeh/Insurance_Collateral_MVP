package com.insurance.mvp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "پاسخ استعلام یا ثبت وثیقه شامل مشخصات وثیقه")
public class MaxAmountResponse {
    @Schema(description = "کد یکتای بیمه‌نامه", example = "80003838503")
    private String ciiNumber;

    @Schema(description = "کد ملی بیمه‌گذار", example = "0492004986")
    private String nationalCode;

    @Schema(description = "مدت زمان وثیقه (ماه)", example = "3")
    private short period;

    @Schema(description = "کد رهگیری ثبت وثیقه", example = "120")
    private long id;

    @Schema(description = "حداکثر مبلغ قابل وثیقه سپاری", example = "43716970.00")
    private BigDecimal maxAmount;

    @Schema(description = "مبلغ نهایی وثیقه بر اساس درخواست", example = "10000000")
    private BigDecimal amount;

    @Schema(description = "شناسه ملی شرکت وثیقه‌گیر", example = "10101541927")
    private String assigneeCompanyCode;

    @Schema(description = "کد رایانه‌ای وثیقه‌گیر", example = "6985014")
    private int assigneeId;
}
