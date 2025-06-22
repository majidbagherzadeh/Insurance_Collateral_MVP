package com.insurance.mvp.service;

import com.insurance.mvp.dto.*;
import com.insurance.mvp.entity.CollateralEntity;
import jakarta.validation.Valid;

import java.util.List;

public interface CollateralService {
    AuthResponse authenticate(TokenRequest request);
    MaxAmountResponse getMaxAmount(MaxAmountRequest request);
    CollateralResponse requestCollateral(MaxAmountRequest request);
    CollateralResponse confirmCollateral(String collateralId, AmountRequest request);
    CollateralResponse cancelCollateral(String collateralId);
    ReserveWithdrawResponse withdrawReserve(String collateralId, AmountRequest request);
    ReleasesResponse releaseCollateral(String collateralId, AmountRequest request);
    List<PaymentStatusResponse> getDraftStatus(int draftId);
    List<FullCollateralResponse> getCollaterals(@Valid CollateralsRequest request);
}