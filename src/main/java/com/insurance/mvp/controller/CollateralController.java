package com.insurance.mvp.controller;

import com.insurance.mvp.dto.*;
import com.insurance.mvp.service.CollateralService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/collaterals")
@Tag(name = "Collateral Services", description = "سرویس‌های وثیقه سپاری بیمه‌نامه‌های زندگی")
public class CollateralController {
    private final CollateralService collateralService;

    public CollateralController(CollateralService collateralService) {
        this.collateralService = collateralService;
    }

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
        return ResponseEntity.ok(collateralService.getMaxAmount(request));
    }

    @PostMapping("/requests")
    @Operation(summary = "درخواست وثیقه سپاری")
    public ResponseEntity<CollateralResponse> requestCollateral(@Valid @RequestBody MaxAmountRequest request) {
        return ResponseEntity.ok(collateralService.requestCollateral(request));
    }

    @PostMapping("/{collateralId}/confirmation")
    @Operation(summary = "تایید وثیقه سپاری")
    public ResponseEntity<CollateralResponse> confirmCollateral(
            @PathVariable int collateralId, @Valid @RequestBody AmountRequest request) {
        return ResponseEntity.ok(collateralService.confirmCollateral(collateralId, request));
    }

    @PostMapping("/{collateralId}/cancellation")
    @Operation(summary = "ابطال وثیقه")
    public ResponseEntity<CollateralResponse> cancelCollateral(
            @PathVariable int collateralId) {
        return ResponseEntity.ok(collateralService.cancelCollateral(collateralId));
    }

    @PostMapping("/{collateralId}/reserve-withdrawals")
    @Operation(summary = "اعلام برداشت از اندوخته")
    public ResponseEntity<ReserveWithdrawResponse> withdrawReserve(
            @PathVariable int collateralId, @Valid @RequestBody AmountRequest request) {
        return ResponseEntity.ok(collateralService.withdrawReserve(collateralId, request));
    }

    @PostMapping("/{collateralId}/releases")
    @Operation(summary = "آزادسازی وثیقه")
    public ResponseEntity<ReserveWithdrawResponse> releaseCollateral(
            @PathVariable int collateralId, @Valid @RequestBody AmountRequest request) {
        return ResponseEntity.ok(collateralService.releaseCollateral(collateralId, request));
    }

    @GetMapping("/draft/inquiry")
    @Operation(summary = "اعلام وضعیت مالی حواله")
    public ResponseEntity<List<PaymentStatusResponse>> getDraftStatus(@RequestParam int draftId) {
        return ResponseEntity.ok(collateralService.getDraftStatus(draftId));
    }
}
