package com.insurance.mvp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "collaterals.collateralsRequest.description", example = "{\n  \"ciiNumber\": \"80003838503\",\n  \"assigneeCompanyCode\": \"10101541927\",\n  \"nationalCode\": \"0492004986\"\n}")
public class CollateralsRequest {
    @Schema(description = "collaterals.nationalCode", example = "0492004986")
    @NotBlank(message = "collaterals.nationalCode.notNull")
    private String nationalCode;

    @Schema(description = "collaterals.ciiNumber", example = "80003838503")
    @NotBlank(message = "collaterals.ciiNumber.notNull")
    private String ciiNumber;

    @Schema(description = "collaterals.assigneeCompanyCode", example = "10101541927")
    @NotBlank(message = "collaterals.assigneeCompanyCode.notNull")
    private String assigneeCompanyCode;
}
