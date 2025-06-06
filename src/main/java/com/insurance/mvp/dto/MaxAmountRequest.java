package com.insurance.mvp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "درخواست استعلام حداکثر مبلغ وثیقه یا ثبت وثیقه جدید", example = "{\n  \"CIINumber\": \"80003838503\",\n  \"AssigneeCompanyCode\": \"10101541927\",\n  \"NationalCode\": \"0492004986\",\n  \"Period\": 3\n}")
public class MaxAmountRequest {
    @Schema(description = "کد ملی", example = "0492004986")
    @NotBlank(message = "کد ملی نباید خالی باشد")
    private String nationalCode;

    @Schema(description = "کد یکتای بیمه‌نامه", example = "80003838503")
    @NotBlank(message = "کد یکتای بیمه‌نامه الزامی است")
    private String cIINumber;

    @Schema(description = "مدت وثیقه (به ماه)", example = "18")
    @NotNull(message = "مدت وثیقه الزامی است")
    @Min(value = 1, message = "مدت وثیقه باید حداقل 1 ماه باشد")
    private short period;

    @Schema(description = "شناسه ملی شرکت وثیقه‌گیر", example = "10101541927")
    @NotBlank(message = "شناسه ملی وثیقه‌گیر الزامی است")
    private String assigneeCompanyCode;
}
