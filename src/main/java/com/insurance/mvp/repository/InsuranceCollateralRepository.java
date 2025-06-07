package com.insurance.mvp.repository;

import com.insurance.mvp.entity.MockInsuranceCollateral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InsuranceCollateralRepository extends JpaRepository<MockInsuranceCollateral, Long> {
    public Optional<MockInsuranceCollateral> findByNationalCodeAndCiiNumberAndAssigneeCompanyCode(String nationalCode, String ciiNumber, String assigneeCompanyCode);
}
