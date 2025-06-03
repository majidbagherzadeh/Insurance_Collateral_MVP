package com.insurance.mvp.service;

import com.insurance.mvp.dto.*;

import java.util.List;

public interface CollateralService {
    AuthResponse authenticate(TokenRequest request);
    MaxAmountResponse getMaxAmount(MaxAmountRequest request);
    CollateralRequestResponse requestCollateral(MaxAmountRequest request);
    CollateralRequestResponse confirmCollateral(int id, ConfirmAmountRequest request);
    CollateralRequestResponse cancelCollateral(int id);
    ReserveWithdrawResponse withdrawReserve(int id, ConfirmAmountRequest request);
    ReserveWithdrawResponse releaseCollateral(int id, ConfirmAmountRequest request);
    List<PaymentStatusResponse> getDraftStatus(int draftId);
}