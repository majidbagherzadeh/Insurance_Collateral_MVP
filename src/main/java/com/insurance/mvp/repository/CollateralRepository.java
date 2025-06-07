package com.insurance.mvp.repository;

import com.insurance.mvp.entity.CollateralEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CollateralRepository extends JpaRepository<CollateralEntity, Long> {
    public List<CollateralEntity> findAllByNationalCodeAndCiiNumberAndAssigneeCompanyCode(String nationalCode, String ciiNumber, String assigneeCompanyCode);
}
