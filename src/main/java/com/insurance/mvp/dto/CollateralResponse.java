package com.insurance.mvp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Schema(description = "پاسخ کامل عملیات وثیقه‌گذاری شامل تاریخ‌ها و وضعیت‌ها")
public class CollateralResponse {
    @Schema(description = "کد یکتای بیمه‌نامه", example = "80003838503")
    private String cIINumber;

    @Schema(description = "کد ملی شخص", example = "0492004986")
    private String nationalCode;

    @Schema(description = "مدت وثیقه (یه ماه)", example = "3")
    private short period;

    @Schema(description = "کد رهگیری", example = "120")
    private int id;

    @Schema(description = "حداکثر مبلغ قابل وثیقه سپاری", example = "43716970.00")
    private BigDecimal maxAmount;

    @Schema(description = "مبلغ نهایی وثیقه", example = "10000000")
    private BigDecimal amount;

    @Schema(description = "مبلغ باقیمانده وثیقه", example = "3500000.00")
    private BigDecimal remainedAmount;

    @Schema(description = "تاریخ شروع وثیقه (شمسی)", example = "1401/12/09")
    private String beginDate;

    @Schema(description = "تاریخ پایان بیمه‌نامه (شمسی)", example = "1405/07/09")
    private String endDate;

    @Schema(description = "زمان ثبت اطلاعات در سیستم", example = "2023-02-28T14:33:04")
    private LocalDateTime creationTime;

    @Schema(description = "تاریخ تایید عملیات (شمسی)", example = "1401/12/01")
    private String confirmationDate;

    @Schema(description = "زمان تایید عملیات", example = "2023-02-20T21:35:51")
    private LocalDateTime confirmationTime;

    @Schema(description = "تاریخ ابطال عملیات (شمسی)", example = "1401/12/01")
    private String cancellationDate;

    @Schema(description = "زمان ابطال عملیات", example = "2023-02-20T21:38:11")
    private LocalDateTime cancellationTime;

    @Schema(description = "شناسه ملی شرکت وثیقه‌گیر", example = "10101541927")
    private String assigneeCompanyCode;

    @Schema(description = "کد رایانه‌ای شخص وثیقه‌گیر", example = "6985014")
    private int assigneeId;
}
