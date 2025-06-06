package com.insurance.mvp.service;

import com.insurance.mvp.dto.*;

import java.util.List;

public interface CollateralService {
    AuthResponse authenticate(TokenRequest request);
    MaxAmountResponse getMaxAmount(MaxAmountRequest request);
    CollateralRequestResponse requestCollateral(MaxAmountRequest request);
    CollateralRequestResponse confirmCollateral(int id, AmountRequest request);
    CollateralRequestResponse cancelCollateral(int id);
    ReserveWithdrawResponse withdrawReserve(int id, AmountRequest request);
    ReserveWithdrawResponse releaseCollateral(int id, AmountRequest request);
    List<PaymentStatusResponse> getDraftStatus(int draftId);
}