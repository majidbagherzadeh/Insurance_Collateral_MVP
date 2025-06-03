package com.insurance.mvp;

import com.insurance.mvp.dto.CollateralRequestResponse;
import com.insurance.mvp.service.CollateralServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CollateralCancellationServiceTest {
    @InjectMocks
    private CollateralServiceImpl service;

    @BeforeEach
    void setUp() { MockitoAnnotations.openMocks(this); }

    @Test
    void cancelCollateral_shouldReturnCancelledCollateral() {
        CollateralRequestResponse res = service.cancelCollateral(24);
        assertThat(res).isNotNull();
    }

    @Test
    void cancelCollateral_withZeroId_shouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> service.cancelCollateral(0));
    }
}