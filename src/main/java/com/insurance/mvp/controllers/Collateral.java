package com.insurance.mvp.controllers;

import com.insurance.mvp.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/collaterals")
@Tag(name = "Collateral Services", description = "سرویس‌های وثیقه سپاری بیمه‌نامه‌های زندگی")
public class Collateral {

    @PostMapping("/auth")
    @Operation(summary = "احراز هویت", description = "دریافت توکن احراز هویت")
    public ResponseEntity<AuthResponse> authenticate(
            @Valid @RequestBody TokenRequest request) {
        return ResponseEntity.ok(new AuthResponse("dummy-token"));
    }

    @PostMapping("/max-amount-inquiry")
    @Operation(summary = "استعلام حداکثر مبلغ قابل وثیقه سپاری")
    public ResponseEntity<MaxAmountResponse> getMaxAmount(
            @Valid @RequestBody MaxAmountRequest request) {
        return ResponseEntity.ok(new MaxAmountResponse());
    }

    @PostMapping("/requests")
    @Operation(summary = "درخواست وثیقه سپاری")
    public ResponseEntity<CollateralRequestResponse> requestCollateral(@Valid @RequestBody MaxAmountRequest request) {
        return ResponseEntity.ok(new CollateralRequestResponse());
    }

    @PostMapping("/{collateralId}/confirmation")
    @Operation(summary = "تایید وثیقه سپاری")
    public ResponseEntity<CollateralRequestResponse> confirmCollateral(
            @PathVariable int collateralId, @Valid @RequestBody AmountRequest request) {
        return ResponseEntity.ok(new CollateralRequestResponse());
    }

    @PostMapping("/{collateralId}/cancellation")
    @Operation(summary = "ابطال وثیقه")
    public ResponseEntity<CollateralRequestResponse> cancelCollateral(
            @PathVariable int collateralId) {
        return ResponseEntity.ok(new CollateralRequestResponse());
    }

    @PostMapping("/{collateralId}/reserve-withdrawals")
    @Operation(summary = "اعلام برداشت از اندوخته")
    public ResponseEntity<ReserveWithdrawResponse> withdrawReserve(
            @PathVariable int collateralId, @Valid @RequestBody AmountRequest request) {
        return ResponseEntity.ok(new ReserveWithdrawResponse());
    }

    @PostMapping("/{collateralId}/releases")
    @Operation(summary = "آزادسازی وثیقه")
    public ResponseEntity<ReserveWithdrawResponse> releaseCollateral(
            @PathVariable int collateralId, @Valid @RequestBody AmountRequest request) {
        return ResponseEntity.ok(new ReserveWithdrawResponse());
    }

    @GetMapping("/draft/inquiry")
    @Operation(summary = "اعلام وضعیت مالی حواله")
    public ResponseEntity<List<PaymentStatusResponse>> getDraftStatus(@RequestParam int draftId) {
        return ResponseEntity.ok(List.of(new PaymentStatusResponse()));
    }
}
