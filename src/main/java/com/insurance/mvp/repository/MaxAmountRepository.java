package com.insurance.mvp.repository;

import com.insurance.mvp.entity.MaxAmountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MaxAmountRepository extends JpaRepository<MaxAmountEntity, String> {
    public Optional<MaxAmountEntity> findByNationalCodeAndCiiNumberAndAssigneeCompanyCode(String nationalCode, String ciiNumber, String assigneeCompanyCode);
}
