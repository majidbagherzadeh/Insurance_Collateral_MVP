package com.insurance.mvp;

import com.insurance.mvp.dto.ConfirmAmountRequest;
import com.insurance.mvp.dto.ReserveWithdrawResponse;
import com.insurance.mvp.service.CollateralServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CollateralReleaseServiceTest {
    @InjectMocks
    private CollateralServiceImpl service;

    @BeforeEach
    void setUp() { MockitoAnnotations.openMocks(this); }

    @Test
    void releaseCollateral_shouldReturnReleaseResponse() {
        ConfirmAmountRequest req = new ConfirmAmountRequest();
        req.setAmount(BigDecimal.valueOf(200000));
        ReserveWithdrawResponse res = service.releaseCollateral(10, req);
        assertThat(res).isNotNull();
    }

    @Test
    void releaseCollateral_withNullAmount_shouldThrow() {
        ConfirmAmountRequest req = new ConfirmAmountRequest();
        req.setAmount(null);

        assertThrows(IllegalArgumentException.class, () -> service.releaseCollateral(5, req));
    }
}