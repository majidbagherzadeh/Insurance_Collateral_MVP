package com.insurance.mvp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "درخواست استعلام حداکثر مبلغ وثیقه یا ثبت وثیقه جدید")
public class MaxAmountRequest {
    @Schema(description = "کد یکتای بیمه‌نامه", example = "80003838503")
    @NotBlank
    private String CIINumber;

    @Schema(description = "کد ملی بیمه‌گذار", example = "0492004986")
    @NotBlank
    private String NationalCode;

    @Schema(description = "مدت زمان وثیقه (بر حسب ماه)", example = "3")
    @NotNull
    private short Period;

    @Schema(description = "شناسه ملی شرکت وثیقه‌گیر", example = "10101541927")
    @NotBlank
    private String AssigneeCompanyCode;
}
