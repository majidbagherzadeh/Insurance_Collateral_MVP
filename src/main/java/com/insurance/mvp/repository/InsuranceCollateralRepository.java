package com.insurance.mvp.repository;

import com.insurance.mvp.entity.InsuranceCollateral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsuranceCollateralRepository extends JpaRepository<InsuranceCollateral, Long> {
}
