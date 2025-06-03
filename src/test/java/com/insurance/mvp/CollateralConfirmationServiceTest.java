package com.insurance.mvp;

import com.insurance.mvp.dto.CollateralRequestResponse;
import com.insurance.mvp.dto.ConfirmAmountRequest;
import com.insurance.mvp.service.CollateralServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CollateralConfirmationServiceTest {
    @InjectMocks
    private CollateralServiceImpl service;

    @BeforeEach
    void setUp() { MockitoAnnotations.openMocks(this); }

    @Test
    void confirmCollateral_shouldReturnUpdatedCollateral() {
        ConfirmAmountRequest req = new ConfirmAmountRequest();
        req.setAmount(BigDecimal.valueOf(1000000));
        CollateralRequestResponse res = service.confirmCollateral(23, req);
        assertThat(res).isNotNull();
    }

    @Test
    void confirmCollateral_withNegativeAmount_shouldThrow() {
        ConfirmAmountRequest req = new ConfirmAmountRequest();
        req.setAmount(BigDecimal.valueOf(-1));

        assertThrows(IllegalArgumentException.class, () -> service.confirmCollateral(10, req));
    }
}