package com.insurance.mvp.service;

import com.insurance.mvp.dto.*;
import com.insurance.mvp.entity.MockInsuranceEntity;
import com.insurance.mvp.entity.CollateralEntity;
import com.insurance.mvp.entity.ReleaseCollateralEntity;
import com.insurance.mvp.entity.WithdrawReserveEntity;
import com.insurance.mvp.exceptions.*;
import com.insurance.mvp.repository.InsuranceCollateralRepository;
import com.insurance.mvp.repository.CollateralRepository;
import com.insurance.mvp.util.DateConverterUtil;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CollateralServiceImpl implements CollateralService {
    private final ModelMapper modelMapper;
    private final InsuranceCollateralRepository insuranceCollateralRepository;
    private final CollateralRepository collateralRepository;

    public CollateralServiceImpl(ModelMapper modelMapper, InsuranceCollateralRepository insuranceCollateralRepository
            , CollateralRepository collateralRepository) {
        this.modelMapper = modelMapper;
        this.insuranceCollateralRepository = insuranceCollateralRepository;
        this.collateralRepository = collateralRepository;
    }

    @Override
    public AuthResponse authenticate(TokenRequest request) {
        return new AuthResponse(request.getToken());
    }

    @Override
    @Transactional
    public MaxAmountResponse getMaxAmount(@Valid MaxAmountRequest maxAmountRequest) {
        MockInsuranceEntity insuranceCollateral = findInsuranceCollateral(maxAmountRequest);

        MaxAmountResponse maxAmountResponse = modelMapper.map(insuranceCollateral, MaxAmountResponse.class);
        maxAmountResponse.setPeriod(maxAmountRequest.getPeriod());
        maxAmountResponse.setAmount(null);

        List<CollateralEntity> collateralEntities = collateralRepository.findAllByNationalCodeAndCiiNumberAndAssigneeCompanyCode(
                maxAmountRequest.getNationalCode(), maxAmountRequest.getCiiNumber(), maxAmountRequest.getAssigneeCompanyCode()
        );

        if (collateralEntities != null && !collateralEntities.isEmpty()) {
            for (CollateralEntity collateralEntity : collateralEntities) {
                collateralEntity.setMaxAmount(maxAmountResponse.getMaxAmount());
                collateralEntity.setEndDate(insuranceCollateral.getEndDate());
            }

            collateralRepository.saveAll(collateralEntities);
        }

        return maxAmountResponse;
    }

    @Override
    @Transactional
    public CollateralResponse requestCollateral(MaxAmountRequest maxAmountRequest) {
        MockInsuranceEntity insuranceCollateral = findInsuranceCollateral(maxAmountRequest);

        CollateralEntity collateralEntity = modelMapper.map(insuranceCollateral, CollateralEntity.class);
        collateralEntity.setAmount(null);
        collateralEntity.setCreationTime(DateConverterUtil.toLocalDateTime(new Date()));
        collateralEntity.setPeriod(maxAmountRequest.getPeriod());
        collateralEntity.setId(0);

        collateralEntity = collateralRepository.save(collateralEntity);

        return modelMapper.map(collateralEntity, CollateralResponse.class);
    }

    @Override
    @Transactional
    public CollateralResponse confirmCollateral(String collateralId, AmountRequest amountRequest) {
        Optional<CollateralEntity> optionalCollateral = collateralRepository.findById(collateralId);

        if (optionalCollateral.isEmpty()) throw new CollateralNotFoundException();
        else {
            CollateralEntity collateralEntity = optionalCollateral.get();

            validNotCanceled(collateralEntity);
            validNotConfirmed(collateralEntity);
            validMaxAmounts(collateralEntity, amountRequest.getAmount());

            collateralEntity.setAmount(amountRequest.getAmount());

            LocalDateTime beginDate = DateConverterUtil.toLocalDateTime(new Date());
            LocalDateTime endDate = beginDate.plusMonths(collateralEntity.getPeriod());

            collateralEntity.setBeginDate(beginDate);
            collateralEntity.setEndDate(endDate);
            collateralEntity.setConfirmationDate(beginDate);
            collateralEntity.setConfirmationTime(beginDate);

            collateralEntity = collateralRepository.save(collateralEntity);

            CollateralResponse collateralResponse = modelMapper.map(collateralEntity, CollateralResponse.class);
            collateralResponse.setRemainedAmount(collateralEntity.calculateRemainedAmount());
            return collateralResponse;
        }
    }

    @Override
    @Transactional
    public CollateralResponse cancelCollateral(String collateralId) {
        Optional<CollateralEntity> optionalCollateral = collateralRepository.findById(collateralId);

        if (optionalCollateral.isEmpty()) throw new CollateralNotFoundException();
        else {
            CollateralEntity collateralEntity = optionalCollateral.get();

            validActive(collateralEntity);

            collateralEntity.setCancellationDate(DateConverterUtil.toLocalDateTime(new Date()));
            collateralEntity.setCancellationTime(DateConverterUtil.toLocalDateTime(new Date()));
            collateralEntity = collateralRepository.save(collateralEntity);

            return modelMapper.map(collateralEntity, CollateralResponse.class);
        }
    }

    @Override
    @Transactional
    public ReserveWithdrawResponse withdrawReserve(String collateralId, AmountRequest amountRequest) {
        Optional<CollateralEntity> optionalCollateral = collateralRepository.findById(collateralId);

        if (optionalCollateral.isEmpty()) throw new CollateralNotFoundException();
        else {
            CollateralEntity collateralEntity = optionalCollateral.get();

            validActive(collateralEntity);
            validRemainAmount(collateralEntity, amountRequest.getAmount());

            WithdrawReserveEntity withdrawReserveEntity = new WithdrawReserveEntity();
            withdrawReserveEntity.setAmount(amountRequest.getAmount());
            withdrawReserveEntity.setCollateralEntity(collateralEntity);

            List<WithdrawReserveEntity> withdrawReserve = collateralEntity.getWithdrawReserve();
            if (withdrawReserve == null)
                collateralEntity.setWithdrawReserve(Collections.singletonList(withdrawReserveEntity));
            else withdrawReserve.add(withdrawReserveEntity);

            collateralEntity = collateralRepository.save(collateralEntity);

            ReserveWithdrawResponse reserveWithdrawResponse = modelMapper.map(collateralEntity, ReserveWithdrawResponse.class);
            reserveWithdrawResponse.setId(withdrawReserveEntity.getId());
            reserveWithdrawResponse.setAmount(amountRequest.getAmount());
            reserveWithdrawResponse.setCollateralId(collateralEntity.getId());
            reserveWithdrawResponse.setRemainedAmount(collateralEntity.calculateRemainedAmount());

            return reserveWithdrawResponse;
        }
    }

    @Override
    @Transactional
    public ReleasesResponse releaseCollateral(String collateralId, AmountRequest amountRequest) {
        Optional<CollateralEntity> optionalCollateral = collateralRepository.findById(collateralId);

        if (optionalCollateral.isEmpty()) throw new CollateralNotFoundException();
        else {
            CollateralEntity collateralEntity = optionalCollateral.get();

            validActive(collateralEntity);
            validRemainAmount(collateralEntity, amountRequest.getAmount());

            ReleaseCollateralEntity releaseCollateralEntity = new ReleaseCollateralEntity();
            releaseCollateralEntity.setAmount(amountRequest.getAmount());
            releaseCollateralEntity.setCollateralEntity(collateralEntity);

            List<ReleaseCollateralEntity> releaseCollaterals = collateralEntity.getReleaseCollaterals();
            if (releaseCollaterals == null)
                collateralEntity.setReleaseCollaterals(Collections.singletonList(releaseCollateralEntity));
            else releaseCollaterals.add(releaseCollateralEntity);

            collateralEntity = collateralRepository.save(collateralEntity);

            ReleasesResponse releasesResponse = modelMapper.map(collateralEntity, ReleasesResponse.class);
            releasesResponse.setId(releasesResponse.getId());
            releasesResponse.setAmount(amountRequest.getAmount());
            releasesResponse.setCollateralId(collateralEntity.getId());
            releasesResponse.setRemainedAmount(collateralEntity.calculateRemainedAmount());

            return releasesResponse;
        }
    }

    @Override
    public List<PaymentStatusResponse> getDraftStatus(int draftId) {
        return List.of();
    }

    @Override
    public List<CollateralResponse> getCollaterals(CollateralsRequest request) {
        List<CollateralEntity> collateralEntities = collateralRepository
                .findAllByNationalCodeAndCiiNumberAndAssigneeCompanyCode(
                        request.getNationalCode(), request.getCiiNumber(), request.getAssigneeCompanyCode()
                );

        return collateralEntities.stream()
                .map(collateralEntity -> modelMapper.map(collateralEntity, CollateralResponse.class))
                .collect(Collectors.toCollection(() -> new ArrayList<>(collateralEntities.size())));
    }

    private MockInsuranceEntity findInsuranceCollateral(MaxAmountRequest maxAmountRequest) {
        Optional<MockInsuranceEntity> mockInsuranceCollateralOption = insuranceCollateralRepository
                .findByNationalCodeAndCiiNumberAndAssigneeCompanyCode(maxAmountRequest.getNationalCode()
                        , maxAmountRequest.getCiiNumber(), maxAmountRequest.getAssigneeCompanyCode());

        if (mockInsuranceCollateralOption.isEmpty()) throw new InsuranceNotFoundException();

        validCollateralExpireMonth(mockInsuranceCollateralOption.get().getEndDate(), maxAmountRequest.getPeriod());

        return mockInsuranceCollateralOption.get();
    }

    private void validCollateralExpireMonth(LocalDateTime endDate, short period) {
        LocalDateTime periodEndDate = DateConverterUtil.toLocalDateTime(new Date()).plusMonths(period);

        if (endDate.isBefore(periodEndDate)) throw new CollateralExpireException(endDate);
    }

    private BigDecimal sumOfActiveCollaterals(String nationalCode, String ciiNumber, String assigneeCompanyCode) {
        BigDecimal sum = BigDecimal.ZERO;

        List<CollateralEntity> collateralEntities = collateralRepository.
                findAllByNationalCodeAndCiiNumberAndAssigneeCompanyCode(nationalCode, ciiNumber, assigneeCompanyCode);
        for (CollateralEntity collateralEntity : collateralEntities)
            if (collateralEntity.nowActive()) sum = sum.add(collateralEntity.calculateRemainedAmount());
        return sum;
    }

    private void validMaxAmounts(CollateralEntity collateralEntity, BigDecimal amount) {
        BigDecimal sumOfActiveCollaterals = sumOfActiveCollaterals(collateralEntity.getNationalCode()
                , collateralEntity.getCiiNumber(), collateralEntity.getAssigneeCompanyCode());
        if (amount.add(sumOfActiveCollaterals).compareTo(collateralEntity.getMaxAmount()) > 0)
            throw new CollateralMaxAmountException();
    }

    private void validNotConfirmed(CollateralEntity collateralEntity) {
        if (collateralEntity.getConfirmationDate() != null || collateralEntity.getConfirmationTime() != null)
            throw new CollateralConfirmedBeforeException();
    }

    private void validNotCanceled(CollateralEntity collateralEntity) {
        if (collateralEntity.canceled()) throw new CollateralCanceledBeforeException();
    }

    private void validActive(CollateralEntity collateralEntity) {
        if (!collateralEntity.nowActive()) throw new CollateralDeActivatedException();
    }

    private void validRemainAmount(CollateralEntity collateralEntity, BigDecimal amount) {
        if (collateralEntity.calculateRemainedAmount().subtract(amount).compareTo(BigDecimal.ZERO) < 0)
            throw new CollateralMaxAmountException();
    }
}
