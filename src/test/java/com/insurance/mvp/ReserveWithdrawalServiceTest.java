package com.insurance.mvp;

//import com.insurance.mvp.dto.AmountRequest;
//import com.insurance.mvp.dto.ReserveWithdrawResponse;
//import com.insurance.mvp.service.CollateralServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.MockitoAnnotations;
//
//import java.math.BigDecimal;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//public class ReserveWithdrawalServiceTest {
//    @InjectMocks
//    private CollateralServiceImpl service;
//
//    @BeforeEach
//    void setUp() { MockitoAnnotations.openMocks(this); }
//
//    @Test
//    void withdrawReserve_shouldReturnWithdrawalResponse() {
//        AmountRequest req = new AmountRequest();
//        req.setAmount(BigDecimal.valueOf(300000));
//        ReserveWithdrawResponse res = service.withdrawReserve(10, req);
//        assertThat(res).isNotNull();
//    }
//
//    @Test
//    void withdrawReserve_withZeroAmount_shouldThrow() {
//        AmountRequest req = new AmountRequest();
//        req.setAmount(BigDecimal.ZERO);
//
//        assertThrows(IllegalArgumentException.class, () -> service.withdrawReserve(10, req));
//    }
//}