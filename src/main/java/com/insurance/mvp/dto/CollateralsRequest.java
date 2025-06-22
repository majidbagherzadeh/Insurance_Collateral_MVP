package com.insurance.mvp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "collaterals.collateralsRequest.description", example = "{\n  \"ciiNumber\": \"789\",\n  \"assigneeCompanyCode\": \"123\",\n  \"nationalCode\": \"0081352492\"\n}")
public class CollateralsRequest {
    @Schema(description = "collaterals.nationalCode", example = "0081352492")
    @NotBlank(message = "collaterals.nationalCode.notNull")
    private String nationalCode;

    @Schema(description = "collaterals.ciiNumber", example = "789")
    @NotBlank(message = "collaterals.ciiNumber.notNull")
    private String ciiNumber;

    @Schema(description = "collaterals.assigneeCompanyCode", example = "123")
    @NotBlank(message = "collaterals.assigneeCompanyCode.notNull")
    private String assigneeCompanyCode;
}
