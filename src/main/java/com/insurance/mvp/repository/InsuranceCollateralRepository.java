package com.insurance.mvp.repository;

import com.insurance.mvp.entity.MockInsuranceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InsuranceCollateralRepository extends JpaRepository<MockInsuranceEntity, Long> {
    public Optional<MockInsuranceEntity> findByNationalCodeAndCiiNumberAndAssigneeCompanyCode(String nationalCode, String ciiNumber, String assigneeCompanyCode);
}
