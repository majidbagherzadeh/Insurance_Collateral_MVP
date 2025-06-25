package com.insurance.mvp.service;

import com.insurance.mvp.dto.*;
import com.insurance.mvp.entity.*;
import com.insurance.mvp.exceptions.*;
import com.insurance.mvp.repository.*;
import com.insurance.mvp.util.DateConverterUtil;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class CollateralServiceImpl implements CollateralService {
    private final ModelMapper modelMapper;
    private final InsuranceCollateralRepository insuranceCollateralRepository;
    private final CollateralRepository collateralRepository;
    private final ReleaseCollateralRepository releaseCollateralRepository;
    private final WithdrawReserveRepository withdrawReserveRepository;
    private final MaxAmountRepository maxAmountRepository;

    public CollateralServiceImpl(ModelMapper modelMapper, InsuranceCollateralRepository insuranceCollateralRepository
            , CollateralRepository collateralRepository, ReleaseCollateralRepository releaseCollateralRepository
            , WithdrawReserveRepository withdrawReserveRepository, MaxAmountRepository maxAmountRepository) {
        this.modelMapper = modelMapper;
        this.insuranceCollateralRepository = insuranceCollateralRepository;
        this.collateralRepository = collateralRepository;
        this.releaseCollateralRepository = releaseCollateralRepository;
        this.withdrawReserveRepository = withdrawReserveRepository;
        this.maxAmountRepository = maxAmountRepository;
    }

    @Override
    public AuthResponse authenticate(TokenRequest request) {
        return new AuthResponse(request.getToken());
    }

    @Override
    @Transactional
    public MaxAmountResponse getMaxAmount(@Valid MaxAmountRequest maxAmountRequest) {
        MockInsuranceEntity insuranceCollateral = findInsuranceCollateral(maxAmountRequest);

        MaxAmountEntity maxAmountEntity = findMaxAmount(maxAmountRequest.getNationalCode(), maxAmountRequest.getCiiNumber()
                , maxAmountRequest.getAssigneeCompanyCode());

        MaxAmountEntity updatemaxAmountEntity = new MaxAmountEntity(insuranceCollateral);

        if (maxAmountEntity != null) updatemaxAmountEntity.setId(maxAmountEntity.getId());

        updatemaxAmountEntity = maxAmountRepository.save(updatemaxAmountEntity);

        MaxAmountResponse maxAmountResponse = modelMapper.map(insuranceCollateral, MaxAmountResponse.class);
        maxAmountResponse.setId(updatemaxAmountEntity.getId());
        maxAmountResponse.setPeriod(maxAmountRequest.getPeriod());
        maxAmountResponse.setAmount(null);

        return maxAmountResponse;
    }

    @Override
    @Transactional
    public CollateralResponse requestCollateral(MaxAmountRequest maxAmountRequest) {
        MockInsuranceEntity insuranceCollateral = findInsuranceCollateral(maxAmountRequest);

        MaxAmountEntity maxAmountEntity = findMaxAmount(maxAmountRequest.getNationalCode()
                , maxAmountRequest.getCiiNumber(), maxAmountRequest.getAssigneeCompanyCode());

        if (maxAmountEntity == null) throw new MaxAmountInquiryNotFound();

        CollateralEntity collateralEntity = modelMapper.map(insuranceCollateral, CollateralEntity.class);
        collateralEntity.setAmount(null);
        collateralEntity.setCreationTime(DateConverterUtil.toLocalDateTime(new Date()));
        collateralEntity.setPeriod(maxAmountRequest.getPeriod());

        collateralEntity = collateralRepository.save(collateralEntity);

        CollateralResponse collateralResponse = modelMapper.map(collateralEntity, CollateralResponse.class);
        collateralResponse.setMaxAmount(maxAmountEntity.getMaxAmount());

        return collateralResponse;
    }

    @Override
    @Transactional
    public CollateralResponse confirmCollateral(String collateralId, AmountRequest amountRequest) {
        Optional<CollateralEntity> optionalCollateral = collateralRepository.findById(collateralId);

        if (optionalCollateral.isEmpty()) throw new CollateralNotFoundException();
        else {
            CollateralEntity collateralEntity = optionalCollateral.get();

            MaxAmountEntity maxAmountEntity = findMaxAmount(collateralEntity.getNationalCode()
                    , collateralEntity.getCiiNumber(), collateralEntity.getAssigneeCompanyCode());

            if (maxAmountEntity == null) throw new MaxAmountInquiryNotFound();

            validNotCanceled(collateralEntity);
            validNotConfirmed(collateralEntity);
            validInsuranceRemainedAmount(collateralEntity, amountRequest);

            LocalDateTime beginDate = DateConverterUtil.toLocalDateTime(new Date());

            collateralEntity.setAmount(amountRequest.getAmount());
            collateralEntity.setBeginDate(beginDate);
            collateralEntity.setConfirmationDate(beginDate);
            collateralEntity.setConfirmationTime(beginDate);

            collateralEntity = collateralRepository.save(collateralEntity);

            CollateralResponse collateralResponse = modelMapper.map(collateralEntity, CollateralResponse.class);
            collateralResponse.setRemainedAmount(calculateInsuranceRemainedAmount(collateralEntity.getNationalCode()
                    , collateralEntity.getCiiNumber(), collateralEntity.getAssigneeCompanyCode()));
            collateralResponse.setMaxAmount(maxAmountEntity.getMaxAmount());

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

            MaxAmountEntity maxAmountEntity = findMaxAmount(collateralEntity.getNationalCode()
                    , collateralEntity.getCiiNumber(), collateralEntity.getAssigneeCompanyCode());

            if (maxAmountEntity == null) throw new MaxAmountInquiryNotFound();

            collateralEntity.setCancellationDate(DateConverterUtil.toLocalDateTime(new Date()));
            collateralEntity.setCancellationTime(DateConverterUtil.toLocalDateTime(new Date()));
            collateralEntity = collateralRepository.save(collateralEntity);

            CollateralResponse collateralResponse = modelMapper.map(collateralEntity, CollateralResponse.class);
            collateralResponse.setMaxAmount(maxAmountEntity.getMaxAmount());

            return collateralResponse;
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
            validCollateralRemainedAmount(amountRequest, collateralEntity);

            WithdrawReserveEntity withdrawReserveEntity = new WithdrawReserveEntity();
            withdrawReserveEntity.setAmount(amountRequest.getAmount());
            withdrawReserveEntity.setCollateralEntity(collateralEntity);

            withdrawReserveEntity = withdrawReserveRepository.save(withdrawReserveEntity);

            List<WithdrawReserveEntity> withdrawReserve = collateralEntity.getWithdrawReserve();
            if (withdrawReserve == null)
                collateralEntity.setWithdrawReserve(Collections.singletonList(withdrawReserveEntity));
            else withdrawReserve.add(withdrawReserveEntity);

            ReserveWithdrawResponse reserveWithdrawResponse = modelMapper.map(collateralEntity, ReserveWithdrawResponse.class);
            reserveWithdrawResponse.setId(withdrawReserveEntity.getId());
            reserveWithdrawResponse.setAmount(amountRequest.getAmount());
            reserveWithdrawResponse.setCollateralId(collateralEntity.getId());
            reserveWithdrawResponse.setRemainedAmount(collateralEntity.getRemainedAmount());

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
            validCollateralRemainedAmount(amountRequest, collateralEntity);

            ReleaseCollateralEntity releaseCollateralEntity = new ReleaseCollateralEntity();
            releaseCollateralEntity.setAmount(amountRequest.getAmount());
            releaseCollateralEntity.setCollateralEntity(collateralEntity);

            releaseCollateralEntity = releaseCollateralRepository.save(releaseCollateralEntity);

            List<ReleaseCollateralEntity> releaseCollaterals = collateralEntity.getReleaseCollaterals();
            if (releaseCollaterals == null)
                collateralEntity.setReleaseCollaterals(Collections.singletonList(releaseCollateralEntity));
            else releaseCollaterals.add(releaseCollateralEntity);

            ReleasesResponse releasesResponse = modelMapper.map(collateralEntity, ReleasesResponse.class);
            releasesResponse.setId(releasesResponse.getId());
            releasesResponse.setAmount(amountRequest.getAmount());
            releasesResponse.setCollateralId(collateralEntity.getId());
            releasesResponse.setRemainedAmount(collateralEntity.getRemainedAmount());

            return releasesResponse;
        }
    }

    @Override
    public List<PaymentStatusResponse> getDraftStatus(int draftId) {
        return List.of();
    }

    @Override
    public List<FullCollateralResponse> getCollaterals(CollateralsRequest request) {
        Optional<MaxAmountEntity> maxAmountEntity = maxAmountRepository
                .findByNationalCodeAndCiiNumberAndAssigneeCompanyCode(
                        request.getNationalCode(), request.getCiiNumber(), request.getAssigneeCompanyCode()
                );

        List<CollateralEntity> collateralEntities = collateralRepository
                .findAllByNationalCodeAndCiiNumberAndAssigneeCompanyCode(
                        request.getNationalCode(), request.getCiiNumber(), request.getAssigneeCompanyCode()
                );
        ArrayList<FullCollateralResponse> fullCollateralResponses = new ArrayList<>(collateralEntities.size());

        BigDecimal remainedAmount = maxAmountEntity.isPresent() ? calculateInsuranceRemainedAmount(
                request.getNationalCode(), request.getCiiNumber(), request.getAssigneeCompanyCode()) : null;

        collateralEntities.forEach(collateralEntity -> {
            FullCollateralResponse fullCollateralResponse = modelMapper.map(collateralEntity, FullCollateralResponse.class);

            if (collateralEntity.getWithdrawReserve() != null) {
                fullCollateralResponse.setReserveWithdrawResponses(new ArrayList<>(collateralEntity.getWithdrawReserve().size()));
                for (WithdrawReserveEntity withdrawReserveEntity : collateralEntity.getWithdrawReserve())
                    fullCollateralResponse.getReserveWithdrawResponses().add(modelMapper.map(withdrawReserveEntity, ReserveWithdrawResponse.class));
            }
            if (collateralEntity.getReleaseCollaterals() != null) {
                fullCollateralResponse.setReleasesResponses(new ArrayList<>(collateralEntity.getReleaseCollaterals().size()));
                for (ReleaseCollateralEntity releaseCollateralEntity : collateralEntity.getReleaseCollaterals())
                    fullCollateralResponse.getReleasesResponses().add(modelMapper.map(releaseCollateralEntity, ReleasesResponse.class));
            }
            maxAmountEntity.ifPresent(entity -> fullCollateralResponse.setMaxAmount(entity.getMaxAmount()));
            fullCollateralResponse.setRemainedAmount(remainedAmount);

            fullCollateralResponses.add(fullCollateralResponse);
        });
        return fullCollateralResponses;
    }

    private BigDecimal calculateInsuranceRemainedAmount(String nationalCode, String ciiNumber, String assigneeCompanyCode) {
        List<CollateralEntity> collateralEntities = collateralRepository
                .findAllByNationalCodeAndCiiNumberAndAssigneeCompanyCode(nationalCode, ciiNumber, assigneeCompanyCode);

        Optional<MaxAmountEntity> maxAmountEntity = maxAmountRepository
                .findByNationalCodeAndCiiNumberAndAssigneeCompanyCode(nationalCode, ciiNumber, assigneeCompanyCode);

        if (maxAmountEntity.isEmpty()) throw new MaxAmountInquiryNotFound();

        if (collateralEntities.isEmpty()) return BigDecimal.ZERO;

        BigDecimal remainedAmount = maxAmountEntity.get().getMaxAmount();

        for (CollateralEntity collateral : collateralEntities) {
            if (collateral.nowActive()) {
                remainedAmount = remainedAmount.subtract(collateral.getAmount());

                for (WithdrawReserveEntity withdrawReserveEntity : collateral.getWithdrawReserve())
                    remainedAmount = remainedAmount.add(withdrawReserveEntity.getAmount());

                for (ReleaseCollateralEntity releaseCollateral : collateral.getReleaseCollaterals())
                    remainedAmount = remainedAmount.add(releaseCollateral.getAmount());
            }
        }


        return remainedAmount;
    }

    private MaxAmountEntity findMaxAmount(String nationalCode, String ciiNumber, String assigneeCompanyCode) {
        Optional<MaxAmountEntity> maxAmountEntity = maxAmountRepository
                .findByNationalCodeAndCiiNumberAndAssigneeCompanyCode(nationalCode, ciiNumber, assigneeCompanyCode);

        return maxAmountEntity.orElse(null);
    }

    private void validInsuranceRemainedAmount(CollateralEntity collateralEntity, AmountRequest amountRequest) {
        BigDecimal insuranceRemainedAmount = calculateInsuranceRemainedAmount(collateralEntity.getNationalCode()
                , collateralEntity.getCiiNumber(), collateralEntity.getAssigneeCompanyCode());

        if (insuranceRemainedAmount.compareTo(amountRequest.getAmount()) < 0) throw new CollateralMaxAmountException();
    }

    private static void validCollateralRemainedAmount(AmountRequest amountRequest, CollateralEntity collateralEntity) {
        BigDecimal collateralRemainedAmount = collateralEntity.getRemainedAmount();
        if (amountRequest.getAmount().compareTo(collateralRemainedAmount) > 0) throw new CollateralMaxAmountException();
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
}
