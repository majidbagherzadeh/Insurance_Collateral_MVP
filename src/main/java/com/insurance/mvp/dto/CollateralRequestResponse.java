package com.insurance.mvp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Schema(description = "پاسخ کامل عملیات وثیقه‌گذاری شامل تاریخ‌ها و وضعیت‌ها")
public class CollateralRequestResponse {
    @Schema(description = "مبلغ باقیمانده وثیقه", example = "3500000.00")
    private BigDecimal RemainedAmount;

    @Schema(description = "تاریخ شروع وثیقه (شمسی)", example = "1401/12/09")
    private String BeginDate;

    @Schema(description = "تاریخ پایان بیمه‌نامه (شمسی)", example = "1405/07/09")
    private String EndDate;

    @Schema(description = "زمان ثبت اطلاعات در سیستم", example = "2023-02-28T14:33:04")
    private LocalDateTime CreationTime;

    @Schema(description = "تاریخ تایید عملیات (شمسی)", example = "1401/12/01")
    private String ConfirmationDate;

    @Schema(description = "زمان تایید عملیات", example = "2023-02-20T21:35:51")
    private LocalDateTime ConfirmationTime;

    @Schema(description = "تاریخ ابطال عملیات (شمسی)", example = "1401/12/01")
    private String CancellationDate;

    @Schema(description = "زمان ابطال عملیات", example = "2023-02-20T21:38:11")
    private LocalDateTime CancellationTime;
}
