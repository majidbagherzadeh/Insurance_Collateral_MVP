package com.insurance.mvp.service;

import com.insurance.mvp.dto.*;
import com.insurance.mvp.entity.MockInsuranceCollateral;
import com.insurance.mvp.entity.CollateralEntity;
import com.insurance.mvp.entity.ReleaseCollateralEntity;
import com.insurance.mvp.repository.InsuranceCollateralRepository;
import com.insurance.mvp.repository.CollateralRepository;
import com.insurance.mvp.util.DateConverterUtil;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

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
    public MaxAmountResponse getMaxAmount(@Valid MaxAmountRequest request) {
        Optional<MockInsuranceCollateral> insuranceCollateral = insuranceCollateralRepository
                .findByNationalCodeAndCiiNumberAndAssigneeCompanyCode(
                        request.getNationalCode(), request.getCiiNumber(), request.getAssigneeCompanyCode()
                );

        if (insuranceCollateral.isPresent()) {
            MaxAmountResponse maxAmountResponse = modelMapper.map(insuranceCollateral.get(), MaxAmountResponse.class);
            maxAmountResponse.setPeriod(request.getPeriod());

            List<CollateralEntity> collateralEntities = collateralRepository.findAllByNationalCodeAndCiiNumberAndAssigneeCompanyCode(
                    request.getNationalCode(), request.getCiiNumber(), request.getAssigneeCompanyCode()
            );

            if (collateralEntities.isEmpty()) {
                CollateralEntity collateralEntity = modelMapper.map(insuranceCollateral.get(), CollateralEntity.class);
                collateralRepository.save(collateralEntity);
            } else {
                for (CollateralEntity collateralEntity : collateralEntities)
                    collateralEntity.setMaxAmount(maxAmountResponse.getMaxAmount());
                collateralRepository.saveAll(collateralEntities);
            }

            return maxAmountResponse;
        } else throw new RuntimeException("اطلاعات برای این وثیقه پیدا نشد");
    }

    @Override
    public CollateralResponse requestCollateral(MaxAmountRequest request) {
        MaxAmountResponse maxAmountResponse = getMaxAmount(request);

        CollateralEntity collateralEntity = modelMapper.map(maxAmountResponse, CollateralEntity.class);
        collateralEntity.setCreationTime(DateConverterUtil.toLocalDateTime(new Date()));

        collateralEntity = collateralRepository.save(collateralEntity);

        return modelMapper.map(collateralEntity, CollateralResponse.class);
    }

    @Override
    public CollateralResponse confirmCollateral(long collateralId, AmountRequest amountRequest) {
        Optional<CollateralEntity> optionalCollateral = collateralRepository.findById(collateralId);

        if (optionalCollateral.isEmpty()) throw new RuntimeException("وثیقه پیدا نشد");
        else {
            CollateralEntity collateralEntity = optionalCollateral.get();

            validNotCanceled(collateralEntity);
            validConfirmed(collateralEntity);
            validMaxAmounts(collateralEntity, amountRequest.getAmount());

            collateralEntity.setAmount(amountRequest.getAmount());

            Date beginDate = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(beginDate);
            calendar.add(Calendar.MONTH, collateralEntity.getPeriod());
            Date endDate = calendar.getTime();

            collateralEntity.setBeginDate(DateConverterUtil.toLocalDateTime(beginDate));
            collateralEntity.setEndDate(DateConverterUtil.toLocalDateTime(endDate));
            collateralEntity.setConfirmationDate(DateConverterUtil.toLocalDateTime(beginDate));
            collateralEntity.setConfirmationTime(DateConverterUtil.toLocalDateTime(beginDate));

            collateralEntity = collateralRepository.save(collateralEntity);

            return modelMapper.map(collateralEntity, CollateralResponse.class);
        }
    }

    @Override
    public CollateralResponse cancelCollateral(long collateralId) {
        Optional<CollateralEntity> optionalCollateral = collateralRepository.findById(collateralId);

        if (optionalCollateral.isEmpty()) throw new RuntimeException("وثیقه پیدا نشد");
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
    public ReserveWithdrawResponse withdrawReserve(long collateralId, AmountRequest request) {
        Optional<CollateralEntity> optionalCollateral = collateralRepository.findById(collateralId);

        if (optionalCollateral.isEmpty()) throw new RuntimeException("وثیقه پیدا نشد");
        else {
            CollateralEntity collateralEntity = optionalCollateral.get();

            validCancelled(collateralEntity);
            validWithdrawReverse(collateralEntity);

            collateralEntity.setWithdrawReserve(true);
            collateralEntity = collateralRepository.save(collateralEntity);

            ReserveWithdrawResponse reserveWithdrawResponse = modelMapper.map(collateralEntity, ReserveWithdrawResponse.class);
            reserveWithdrawResponse.setCollateralId(collateralId);
            return reserveWithdrawResponse;
        }
    }

    @Override
    public ReserveWithdrawResponse releaseCollateral(long collateralId, AmountRequest request) {
        Optional<CollateralEntity> optionalCollateral = collateralRepository.findById(collateralId);

        if (optionalCollateral.isEmpty()) throw new RuntimeException("وثیقه پیدا نشد");
        else {
            CollateralEntity collateralEntity = optionalCollateral.get();

            validActive(collateralEntity);
            validRemainAmount(collateralEntity, request.getAmount());

            ReleaseCollateralEntity releaseCollateralEntity = new ReleaseCollateralEntity();
            releaseCollateralEntity.setAmount(request.getAmount());
            releaseCollateralEntity.setCollateralEntity(collateralEntity);

            List<ReleaseCollateralEntity> withdrawReserve = collateralEntity.getReleaseCollaterals();
            if (withdrawReserve == null)
                collateralEntity.setReleaseCollaterals(Collections.singletonList(releaseCollateralEntity));
            else withdrawReserve.add(releaseCollateralEntity);

            collateralEntity = collateralRepository.save(collateralEntity);

            ReserveWithdrawResponse reserveWithdrawResponse = new ReserveWithdrawResponse();
            reserveWithdrawResponse.setAmount(request.getAmount());
            reserveWithdrawResponse.setRemainedAmount(collateralEntity.calculateRemainedAmount());
            reserveWithdrawResponse.setCollateralId(collateralEntity.getId());

            return modelMapper.map(collateralEntity, ReserveWithdrawResponse.class);
        }
    }

    @Override
    public List<PaymentStatusResponse> getDraftStatus(int draftId) {
        return List.of();
    }

    private BigDecimal sumOfActiveCollaterals(String nationalCode, String ciiNumber, String assigneeCompanyCode) {
        BigDecimal sum = BigDecimal.ZERO;

        List<CollateralEntity> collateralEntities = collateralRepository.findAllByNationalCodeAndCiiNumberAndAssigneeCompanyCode(nationalCode, ciiNumber, assigneeCompanyCode);
        for (CollateralEntity collateralEntity : collateralEntities)
            if (collateralEntity.nowActive()) sum = sum.add(collateralEntity.calculateRemainedAmount());
        return sum;
    }

    private void validMaxAmounts(CollateralEntity collateralEntity, BigDecimal amount) {
        if (amount.add(sumOfActiveCollaterals(collateralEntity.getNationalCode(), collateralEntity.getCiiNumber(), collateralEntity.getAssigneeCompanyCode())).compareTo(collateralEntity.getMaxAmount()) > 0)
            throw new RuntimeException("مبلغ مجموع وثایق روی این بیمه نامه بیشتر از حداکثر مبلغ مجاز است");
    }

    private void validConfirmed(CollateralEntity collateralEntity) {
        if (collateralEntity.getConfirmationDate() != null || collateralEntity.getConfirmationTime() != null)
            throw new RuntimeException("وثیقه قبلا تایید شده است");
    }

    private void validNotCanceled(CollateralEntity collateralEntity) {
        if (collateralEntity.canceled()) throw new RuntimeException("وثیقه قبلا کنسل شده است");
    }

    private void validActive(CollateralEntity collateralEntity) {
        if (!collateralEntity.nowActive()) throw new RuntimeException("وثیقه فعال نیست");
    }

    private void validCancelled(CollateralEntity collateralEntity) {
        if (!collateralEntity.canceled()) throw new RuntimeException("وثیقه فعال است، قبل از آن باید وثیقه را ابطال کنید");
    }

    private void validRemainAmount(CollateralEntity collateralEntity, BigDecimal amount) {
        if (collateralEntity.calculateRemainedAmount().subtract(amount).compareTo(BigDecimal.ZERO) < 0)
            throw new RuntimeException("مبلغ برداشت از باقیمانده اندوخته بیشتر است");
    }

    private void validWithdrawReverse(CollateralEntity collateralEntity) {
        if (collateralEntity.isWithdrawReserve()) throw new RuntimeException("وثیقه پیش از این برداشت از اندوخته شده است");
    }
}
