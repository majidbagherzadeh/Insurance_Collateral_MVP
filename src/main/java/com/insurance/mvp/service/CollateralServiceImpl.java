package com.insurance.mvp.service;

import com.insurance.mvp.dto.*;
import com.insurance.mvp.entity.MockInsuranceCollateral;
import com.insurance.mvp.entity.CollateralEntity;
import com.insurance.mvp.repository.InsuranceCollateralRepository;
import com.insurance.mvp.repository.CollateralRepository;
import com.insurance.mvp.util.DateConverterUtil;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
        collateralEntity.setRemainedAmount(maxAmountResponse.getAmount());
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

            checkCanceled(collateralEntity);
            checkPreConfirm(collateralEntity);
            checkMaxAmount(amountRequest, collateralEntity);

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

            checkNotActive(collateralEntity);

            collateralEntity.setCancellationDate(DateConverterUtil.toLocalDateTime(new Date()));
            collateralEntity.setCancellationTime(DateConverterUtil.toLocalDateTime(new Date()));
            collateralEntity = collateralRepository.save(collateralEntity);

            return modelMapper.map(collateralEntity, CollateralResponse.class);
        }
    }

    @Override
    public ReserveWithdrawResponse withdrawReserve(long collateralId, AmountRequest request) {
        return new ReserveWithdrawResponse();
    }

    @Override
    public ReserveWithdrawResponse releaseCollateral(long collateralId, AmountRequest request) {
        return new ReserveWithdrawResponse();
    }

    @Override
    public List<PaymentStatusResponse> getDraftStatus(int draftId) {
        return List.of();
    }

    private BigDecimal sumOfActiveCollaterals(String nationalCode, String ciiNumber, String assigneeCompanyCode) {
        BigDecimal sum = BigDecimal.ZERO;

        List<CollateralEntity> collateralEntities = collateralRepository.findAllByNationalCodeAndCiiNumberAndAssigneeCompanyCode(nationalCode, ciiNumber, assigneeCompanyCode);
        for (CollateralEntity collateralEntity : collateralEntities)
            if (collateralEntity.nowActive()) sum = sum.add(collateralEntity.getAmount());
        return sum;
    }

    private void checkMaxAmount(AmountRequest amountRequest, CollateralEntity collateralEntity) {
        if (amountRequest.getAmount().add(sumOfActiveCollaterals(collateralEntity.getNationalCode(), collateralEntity.getCiiNumber(), collateralEntity.getAssigneeCompanyCode())).compareTo(collateralEntity.getMaxAmount()) > 0)
            throw new RuntimeException("مبلغ مجموع وثایق روی این بیمه نامه بیشتر از حداکثر مبلغ مجاز است");
    }

    private void checkPreConfirm(CollateralEntity collateralEntity) {
        if (collateralEntity.getConfirmationDate() != null || collateralEntity.getConfirmationTime() != null)
            throw new RuntimeException("وثیقه قبلا تایید شده است");
    }

    private void checkCanceled(CollateralEntity collateralEntity) {
        if (collateralEntity.canceled()) throw new RuntimeException("وثیقه قبلا کنسل شده است");
    }

    private void checkNotActive(CollateralEntity collateralEntity) {
        if (!collateralEntity.nowActive()) throw new RuntimeException("وثیقه فعال نیست");
    }
}
