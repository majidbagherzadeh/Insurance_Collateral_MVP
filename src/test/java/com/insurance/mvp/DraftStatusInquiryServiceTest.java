package com.insurance.mvp;

import com.insurance.mvp.dto.PaymentStatusResponse;
import com.insurance.mvp.service.CollateralServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DraftStatusInquiryServiceTest {
    @InjectMocks
    private CollateralServiceImpl service;

    @BeforeEach
    void setUp() { MockitoAnnotations.openMocks(this); }

    @Test
    void getDraftStatus_shouldReturnList() {
        List<PaymentStatusResponse> res = service.getDraftStatus(941487);
        assertThat(res).isNotEmpty();
    }

    @Test
    void getDraftStatus_withNegativeDraftId_shouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> service.getDraftStatus(-941487));
    }
}