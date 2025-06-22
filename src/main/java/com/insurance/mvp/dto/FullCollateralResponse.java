package com.insurance.mvp.dto;

import com.insurance.mvp.entity.CollateralEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class FullCollateralResponse extends CollateralResponse {
    private List<ReserveWithdrawResponse> reserveWithdrawResponses;
    private List<ReleasesResponse> releasesResponses;
}
