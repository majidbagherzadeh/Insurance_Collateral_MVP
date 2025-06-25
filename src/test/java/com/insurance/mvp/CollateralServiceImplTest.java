//package com.insurance.mvp;
//
//import com.insurance.mvp.dto.*;
//import com.insurance.mvp.entity.CollateralEntity;
//import com.insurance.mvp.entity.MockInsuranceEntity;
//import com.insurance.mvp.exceptions.*;
//import com.insurance.mvp.repository.CollateralRepository;
//import com.insurance.mvp.repository.InsuranceCollateralRepository;
//import com.insurance.mvp.repository.ReleaseCollateralRepository;
//import com.insurance.mvp.repository.WithdrawReserveRepository;
//import com.insurance.mvp.service.CollateralServiceImpl;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Spy;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.modelmapper.ModelMapper;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class CollateralServiceImplTest {
//
//    @Mock
//    private InsuranceCollateralRepository insuranceCollateralRepository;
//
//    @Mock
//    private CollateralRepository collateralRepository;
//
//    @Mock
//    private WithdrawReserveRepository withdrawReserveRepository;
//
//    @Mock
//    private ReleaseCollateralRepository releaseCollateralRepository;
//
//    @InjectMocks
//    private CollateralServiceImpl service;
//
//    @Spy
//    private ModelMapper modelMapper;
//
//    @Test
//    void authenticate_shouldReturnToken() {
//        TokenRequest request = new TokenRequest();
//        request.setToken("test-token");
//        AuthResponse response = service.authenticate(request);
//        assertThat(response.getAccessToken()).isEqualTo("test-token");
//    }
//
//    @Test
//    void getMaxAmount_success() {
//        MaxAmountRequest req = new MaxAmountRequest("nat", "ci", (short) 6, "code");
//        MockInsuranceEntity entity = new MockInsuranceEntity();
//        entity.setId("id");
//        entity.setEndDate(LocalDateTime.now().plusMonths(12));
//        entity.setMaxAmount(BigDecimal.valueOf(1000000));
//
//        when(insuranceCollateralRepository.findByNationalCodeAndCiiNumberAndAssigneeCompanyCode(any(), any(), any()))
//                .thenReturn(Optional.of(entity));
//
//        MaxAmountResponse res = service.getMaxAmount(req);
//        assertThat(res).isNotNull();
//    }
//
//    @Test
//    void getMaxAmount_UpdateMaxAmount_success() {
//        MaxAmountRequest req = new MaxAmountRequest("nat", "ci", (short) 6, "code");
//        MockInsuranceEntity entity = new MockInsuranceEntity();
//        entity.setId("id");
//        LocalDateTime endDate = LocalDateTime.now().plusMonths(12);
//        entity.setEndDate(endDate);
//        entity.setMaxAmount(BigDecimal.valueOf(1000000));
//
//        List<CollateralEntity> collateralEntities = new ArrayList<>(2);
//        CollateralEntity collateralEntity1 = new CollateralEntity();
//        collateralEntity1.setId("id1");
//        collateralEntity1.setMaxAmount(BigDecimal.valueOf(50000));
//        collateralEntity1.setEndDate(LocalDateTime.now().minusMonths(10));
//        CollateralEntity collateralEntity2 = new CollateralEntity();
//        collateralEntity2.setId("id2");
//        collateralEntity2.setMaxAmount(BigDecimal.valueOf(150000));
//        collateralEntity2.setEndDate(LocalDateTime.now().minusMonths(20));
//        collateralEntities.add(collateralEntity1);
//        collateralEntities.add(collateralEntity2);
//
//        when(insuranceCollateralRepository.findByNationalCodeAndCiiNumberAndAssigneeCompanyCode(any(), any(), any()))
//                .thenReturn(Optional.of(entity));
//
//        when(collateralRepository.findAllByNationalCodeAndCiiNumberAndAssigneeCompanyCode(any(), any(), any()))
//                .thenReturn(collateralEntities);
//
//        MaxAmountResponse res = service.getMaxAmount(req);
//        assertThat(res).isNotNull();
//
//        collateralEntities = new ArrayList<>(2);
//        collateralEntity1 = new CollateralEntity();
//        collateralEntity1.setId("id1");
//        collateralEntity1.setMaxAmount(BigDecimal.valueOf(1000000));
//        collateralEntity1.setEndDate(endDate);
//        collateralEntity2 = new CollateralEntity();
//        collateralEntity2.setId("id2");
//        collateralEntity2.setMaxAmount(BigDecimal.valueOf(1000000));
//        collateralEntity2.setEndDate(endDate);
//        collateralEntities.add(collateralEntity1);
//        collateralEntities.add(collateralEntity2);
//
//        verify(collateralRepository, times(1)).saveAll(collateralEntities);
//    }
//
//    @Test
//    void getMaxAmount_notFound_shouldThrow() {
//        MaxAmountRequest req = new MaxAmountRequest("nat", "ci", (short) 6, "code");
//        when(insuranceCollateralRepository.findByNationalCodeAndCiiNumberAndAssigneeCompanyCode(any(), any(), any()))
//                .thenReturn(Optional.empty());
//        assertThrows(InsuranceNotFoundException.class, () -> service.getMaxAmount(req));
//    }
//
//    @Test
//    void requestCollateral_success() {
//        MaxAmountRequest req = new MaxAmountRequest("nat", "ci", (short) 6, "code");
//        MockInsuranceEntity entity = new MockInsuranceEntity();
//        entity.setEndDate(LocalDateTime.now().plusMonths(12));
//        entity.setMaxAmount(BigDecimal.valueOf(5000));
//
//        when(insuranceCollateralRepository.findByNationalCodeAndCiiNumberAndAssigneeCompanyCode(any(), any(), any()))
//                .thenReturn(Optional.of(entity));
//        when(collateralRepository.save(any())).thenAnswer(i -> i.getArgument(0));
//
//        CollateralResponse response = service.requestCollateral(req);
//        assertThat(response).isNotNull();
//    }
//
//    @Test
//    void confirmCollateral_success() {
//        CollateralEntity entity = createdCollateralEntity();
//        when(collateralRepository.findById(any())).thenReturn(Optional.of(entity));
//        when(collateralRepository.save(any())).thenAnswer(i -> i.getArgument(0));
//
//        CollateralResponse response = service.confirmCollateral("1", new AmountRequest(BigDecimal.valueOf(1000)));
//        assertThat(response).isNotNull();
//    }
//
//    @Test
//    void confirmCollateral_notFound_shouldThrow() {
//        when(collateralRepository.findById(any())).thenReturn(Optional.empty());
//        assertThrows(CollateralNotFoundException.class, () -> service.confirmCollateral("1", new AmountRequest(BigDecimal.TEN)));
//    }
//
//    @Test
//    void confirmCollateral_cancelled_shouldThrow() {
//        CollateralEntity entity = canceledCollateralEntity();
//        when(collateralRepository.findById(any())).thenReturn(Optional.of(entity));
//        assertThrows(CollateralCanceledBeforeException.class, () -> service.confirmCollateral("1", new AmountRequest(BigDecimal.TEN)));
//    }
//
//    @Test
//    void confirmCollateral_confirmed_shouldThrow() {
//        CollateralEntity entity = confirmedCollateralEntity();
//        when(collateralRepository.findById(any())).thenReturn(Optional.of(entity));
//        assertThrows(CollateralConfirmedBeforeException.class, () -> service.confirmCollateral("1", new AmountRequest(BigDecimal.TEN)));
//    }
//
//    @Test
//    void cancelCollateral_success() {
//        CollateralEntity entity = confirmedCollateralEntity();
//        when(collateralRepository.findById(any())).thenReturn(Optional.of(entity));
//        when(collateralRepository.save(any())).thenAnswer(i -> i.getArgument(0));
//
//        CollateralResponse response = service.cancelCollateral("1");
//        assertThat(response).isNotNull();
//    }
//
//    @Test
//    void withdrawReserve_shouldThrowWhenNotFound() {
//        when(collateralRepository.findById(any())).thenReturn(Optional.empty());
//        assertThrows(CollateralNotFoundException.class, () -> service.withdrawReserve("1", new AmountRequest(BigDecimal.ONE)));
//    }
//
//    @Test
//    void releaseCollateral_shouldThrowWhenNotFound() {
//        when(collateralRepository.findById(any())).thenReturn(Optional.empty());
//        assertThrows(CollateralNotFoundException.class, () -> service.releaseCollateral("1", new AmountRequest(BigDecimal.ONE)));
//    }
//
//    @Test
//    void withdrawReserve_success() {
//        CollateralEntity entity = confirmedCollateralEntity();
//        entity.setAmount(BigDecimal.valueOf(1000));
//        when(collateralRepository.findById(any())).thenReturn(Optional.of(entity));
//        when(withdrawReserveRepository.save(any())).thenAnswer(i -> i.getArgument(0));
//
//        ReserveWithdrawResponse response = service.withdrawReserve("1", new AmountRequest(BigDecimal.valueOf(500)));
//
//        assertThat(response).isNotNull();
//        assertThat(response.getAmount()).isEqualTo(BigDecimal.valueOf(500));
//    }
//
//    @Test
//    void withdrawReserve_insufficientAmount_shouldThrow() {
//        CollateralEntity entity = createdCollateralEntity();
//        entity.setAmount(BigDecimal.valueOf(500));
//        when(collateralRepository.findById(any())).thenReturn(Optional.of(entity));
//
//        assertThrows(CollateralDeActivatedException.class,
//                () -> service.withdrawReserve("1", new AmountRequest(BigDecimal.valueOf(1000))));
//    }
//
//    @Test
//    void releaseCollateral_success() {
//        CollateralEntity entity = confirmedCollateralEntity();
//        entity.setAmount(BigDecimal.valueOf(1000));
//        when(collateralRepository.findById(any())).thenReturn(Optional.of(entity));
//        when(releaseCollateralRepository.save(any())).thenAnswer(i -> i.getArgument(0));
//
//        ReleasesResponse response = service.releaseCollateral("1", new AmountRequest(BigDecimal.valueOf(500)));
//
//        assertThat(response).isNotNull();
//        assertThat(response.getAmount()).isEqualTo(BigDecimal.valueOf(500));
//    }
//
//    @Test
//    void releaseCollateral_insufficientAmount_shouldThrow() {
//        CollateralEntity entity = createdCollateralEntity();
//        entity.setAmount(BigDecimal.valueOf(500));
//        when(collateralRepository.findById(any())).thenReturn(Optional.of(entity));
//
//        assertThrows(CollateralDeActivatedException.class,
//                () -> service.releaseCollateral("1", new AmountRequest(BigDecimal.valueOf(1000))));
//    }
//
//    @Test
//    void withdrawReserve_invalidCollateral_shouldThrow() {
//        when(collateralRepository.findById(any())).thenReturn(Optional.empty());
//        assertThrows(CollateralNotFoundException.class, () -> service.withdrawReserve("1", new AmountRequest(BigDecimal.ONE)));
//    }
//
//    @Test
//    void releaseCollateral_invalidCollateral_shouldThrow() {
//        when(collateralRepository.findById(any())).thenReturn(Optional.empty());
//        assertThrows(CollateralNotFoundException.class, () -> service.releaseCollateral("1", new AmountRequest(BigDecimal.ONE)));
//    }
//
//    private CollateralEntity createdCollateralEntity() {
//        CollateralEntity entity = new CollateralEntity();
//        entity.setId("id");
//        entity.setNationalCode("nat");
//        entity.setCiiNumber("cii");
//        entity.setAssigneeCompanyCode("code");
//        entity.setMaxAmount(BigDecimal.valueOf(1000000));
//        entity.setCreationTime(LocalDateTime.now());
//        return entity;
//    }
//
//    private CollateralEntity confirmedCollateralEntity() {
//        CollateralEntity entity = createdCollateralEntity();
//        entity.setConfirmationDate(LocalDateTime.now());
//        entity.setConfirmationTime(LocalDateTime.now());
//        return entity;
//    }
//
//    private CollateralEntity canceledCollateralEntity() {
//        CollateralEntity entity = createdCollateralEntity();
//        entity.setCancellationDate(LocalDateTime.now());
//        entity.setCancellationTime(LocalDateTime.now());
//        return entity;
//    }
//}
