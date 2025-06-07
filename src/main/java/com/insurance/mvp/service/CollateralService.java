package com.insurance.mvp.service;

import com.insurance.mvp.dto.*;

import java.util.List;

public interface CollateralService {
    AuthResponse authenticate(TokenRequest request);
    MaxAmountResponse getMaxAmount(MaxAmountRequest request);
    CollateralResponse requestCollateral(MaxAmountRequest request);
    CollateralResponse confirmCollateral(long collateralId, AmountRequest request);
    CollateralResponse cancelCollateral(long collateralId);
    ReserveWithdrawResponse withdrawReserve(long collateralId, AmountRequest request);
    ReserveWithdrawResponse releaseCollateral(long collateralId, AmountRequest request);
    List<PaymentStatusResponse> getDraftStatus(int draftId);
}