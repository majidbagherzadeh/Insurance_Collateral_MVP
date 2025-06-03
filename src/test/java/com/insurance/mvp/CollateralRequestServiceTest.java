package com.insurance.mvp;

import com.insurance.mvp.dto.CollateralRequestResponse;
import com.insurance.mvp.dto.MaxAmountRequest;
import com.insurance.mvp.service.CollateralServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CollateralRequestServiceTest {
    @InjectMocks
    private CollateralServiceImpl service;

    @BeforeEach
    void setUp() { MockitoAnnotations.openMocks(this); }

    @Test
    void requestCollateral_shouldReturnValidResponse() {
        MaxAmountRequest req = new MaxAmountRequest();
        CollateralRequestResponse res = service.requestCollateral(req);
        assertThat(res).isNotNull();
    }

    @Test
    void requestCollateral_withEmptyCIINumber_shouldThrow() {
        MaxAmountRequest req = new MaxAmountRequest();
        req.setCIINumber("");
        req.setNationalCode("001");
        req.setPeriod((short) 6);
        req.setAssigneeCompanyCode("111");

        assertThrows(IllegalArgumentException.class, () -> service.requestCollateral(req));
    }
}
