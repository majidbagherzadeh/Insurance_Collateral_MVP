package com.insurance.mvp.repository;

import com.insurance.mvp.entity.WithdrawReserveEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WithdrawReserveRepository extends JpaRepository<WithdrawReserveEntity, String> {
}
