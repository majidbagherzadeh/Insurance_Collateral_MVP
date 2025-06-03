package com.insurance.mvp;

import com.insurance.mvp.dto.MaxAmountRequest;
import com.insurance.mvp.dto.MaxAmountResponse;
import com.insurance.mvp.service.CollateralServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;

public class MaxAmountInquiryServiceTest {
    @InjectMocks
    private CollateralServiceImpl service;

    @BeforeEach
    void setUp() { MockitoAnnotations.openMocks(this); }

    @Test
    void getMaxAmount_shouldReturnResponse() {
        MaxAmountRequest req = new MaxAmountRequest();
        req.setCIINumber("123");
        MaxAmountResponse res = service.getMaxAmount(req);
        assertThat(res).isNotNull();
    }

    @Test
    void getMaxAmount_withNullNationalCode_shouldThrow() {
        MaxAmountRequest req = new MaxAmountRequest();
        req.setCIINumber("123");
        req.setNationalCode(null);
        req.setPeriod((short) 3);
        req.setAssigneeCompanyCode("111");

        service.getMaxAmount(req);
    }
}