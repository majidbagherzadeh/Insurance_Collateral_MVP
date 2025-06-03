package com.insurance.mvp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "درخواست استعلام حداکثر مبلغ وثیقه یا ثبت وثیقه جدید")
public class MaxAmountRequest {
    @Schema(description = "کد یکتای بیمه‌نامه", example = "80003838503")
    @NotBlank(message = "کد یکتای بیمه نامه الزامی است")
    private String CIINumber;

    @Schema(description = "کد ملی بیمه‌گذار", example = "0492004986")
    @NotBlank(message = "کد ملی نباید خالی باشد")
    private String NationalCode;

    @Schema(description = "مدت زمان وثیقه (بر حسب ماه)", example = "3")
    @NotNull(message = "مدت زمان وثیقه الزامی است")
    @Min(value = 1, message = "مدت وثیقه باید حداقل 1 ماه باشد")
    private short Period;

    @Schema(description = "شناسه ملی شرکت وثیقه‌گیر", example = "10101541927")
    @NotBlank(message = "شناسه ملی وثیقه‌گیر الزامی است")
    private String AssigneeCompanyCode;
}
