package com.insurance.mvp.service;

import com.insurance.mvp.dto.*;
import jakarta.validation.Valid;

import java.util.List;

public class CollateralServiceImpl implements CollateralService {
    @Override
    public AuthResponse authenticate(TokenRequest request) {
        return new AuthResponse(request.getToken());
    }

    @Override
    public MaxAmountResponse getMaxAmount(@Valid MaxAmountRequest request) {
        return new MaxAmountResponse();
    }

    @Override
    public CollateralRequestResponse requestCollateral(MaxAmountRequest request) {
        return new CollateralRequestResponse();
    }

    @Override
    public CollateralRequestResponse confirmCollateral(int id, ConfirmAmountRequest request) {
        return new CollateralRequestResponse();
    }

    @Override
    public CollateralRequestResponse cancelCollateral(int id) {
        return new CollateralRequestResponse();
    }

    @Override
    public ReserveWithdrawResponse withdrawReserve(int id, ConfirmAmountRequest request) {
        return new ReserveWithdrawResponse();
    }

    @Override
    public ReserveWithdrawResponse releaseCollateral(int id, ConfirmAmountRequest request) {
        return new ReserveWithdrawResponse();
    }

    @Override
    public List<PaymentStatusResponse> getDraftStatus(int draftId) {
        return List.of();
    }
}
