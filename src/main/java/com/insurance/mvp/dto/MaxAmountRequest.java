package com.insurance.mvp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "collaterals.maxAmountRequest.description", example = "{\n  \"ciiNumber\": \"80003838503\",\n  \"assigneeCompanyCode\": \"10101541927\",\n  \"nationalCode\": \"0492004986\",\n  \"period\": 18\n}")
public class MaxAmountRequest {
    @Schema(description = "collaterals.nationalCode", example = "0492004986")
    @NotBlank(message = "collaterals.nationalCode.notNull")
    private String nationalCode;

    @Schema(description = "collaterals.ciiNumber", example = "80003838503")
    @NotBlank(message = "collaterals.ciiNumber.notNull")
    private String ciiNumber;

    @Schema(description = "collaterals.period", example = "18")
    @NotNull(message = "collaterals.maxAmountRequest.period.notNull.message")
    @Min(value = 1, message = "collaterals.maxAmountRequest.period.min.message")
    private short period;

    @Schema(description = "collaterals.assigneeCompanyCode", example = "10101541927")
    @NotBlank(message = "collaterals.assigneeCompanyCode.notNull")
    private String assigneeCompanyCode;
}
