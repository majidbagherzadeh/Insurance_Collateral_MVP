package com.insurance.mvp.repository;

import com.insurance.mvp.entity.ReleaseCollateralEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReleaseCollateralRepository extends JpaRepository<ReleaseCollateralEntity, String> {
}
