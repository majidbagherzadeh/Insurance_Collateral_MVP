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
@Tag(name = "collaterals.services.name")
public class CollateralController {
    private final CollateralService collateralService;

    public CollateralController(CollateralService collateralService) {
        this.collateralService = collateralService;
    }

    @PostMapping("/max-amount-inquiry")
    @Operation(summary = "collaterals.max-amount-inquiry.summary")
    public ResponseEntity<MaxAmountResponse> getMaxAmount(
            @Valid @RequestBody MaxAmountRequest request) {
        return ResponseEntity.ok(collateralService.getMaxAmount(request));
    }

    @PostMapping("/requests")
    @Operation(summary = "collaterals.requests.summary")
    public ResponseEntity<CollateralResponse> requestCollateral(@Valid @RequestBody MaxAmountRequest request) {
        return ResponseEntity.ok(collateralService.requestCollateral(request));
    }

    @PostMapping("/{collateralId}/confirmation")
    @Operation(summary = "collaterals.confirmation.summary")
    public ResponseEntity<CollateralResponse> confirmCollateral(
            @PathVariable String collateralId, @Valid @RequestBody AmountRequest request) {
        return ResponseEntity.ok(collateralService.confirmCollateral(collateralId, request));
    }

    @PostMapping("/{collateralId}/cancellation")
    @Operation(summary = "collaterals.cancellation.summary")
    public ResponseEntity<CollateralResponse> cancelCollateral(
            @PathVariable String collateralId) {
        return ResponseEntity.ok(collateralService.cancelCollateral(collateralId));
    }

    @PostMapping("/{collateralId}/reserve-withdrawals")
    @Operation(summary = "collaterals.reserve-withdrawals.summary")
    public ResponseEntity<ReserveWithdrawResponse> withdrawReserve(
            @PathVariable String collateralId, @Valid @RequestBody AmountRequest request) {
        return ResponseEntity.ok(collateralService.withdrawReserve(collateralId, request));
    }

    @PostMapping("/{collateralId}/releases")
    @Operation(summary = "collaterals.releases.summary")
    public ResponseEntity<ReleasesResponse> releaseCollateral(
            @PathVariable String collateralId, @Valid @RequestBody AmountRequest request) {
        return ResponseEntity.ok(collateralService.releaseCollateral(collateralId, request));
    }

    @GetMapping("/draft/inquiry")
    @Operation(summary = "collaterals.inquiry.summary")
    public ResponseEntity<List<PaymentStatusResponse>> getDraftStatus(@RequestParam int draftId) {
        return ResponseEntity.ok(collateralService.getDraftStatus(draftId));
    }

    @GetMapping("/getCollaterals")
    @Operation(summary = "collaterals.getCollaterals.summary")
    public ResponseEntity<List<FullCollateralResponse>> getCollaterals(@RequestParam String ciiNumber
            , @RequestParam String assigneeCompanyCode, @RequestParam String nationalCode) {
        return ResponseEntity.ok(collateralService.getCollaterals(
                new CollateralsRequest(nationalCode, ciiNumber, assigneeCompanyCode))
        );
    }
}
