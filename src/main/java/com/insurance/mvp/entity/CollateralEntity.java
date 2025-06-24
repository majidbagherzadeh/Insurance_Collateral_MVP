package com.insurance.mvp.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "collateral")
@Data
public class CollateralEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String ciiNumber;

    @Column(nullable = false)
    private String nationalCode;

    @Column(nullable = false)
    private short period;

    @Column
    private BigDecimal amount;

    @Column
    private LocalDateTime beginDate;

    @Column(nullable = false)
    private LocalDateTime creationTime;

    @Column
    private LocalDateTime confirmationDate;

    @Column
    private LocalDateTime confirmationTime;

    @Column
    private LocalDateTime cancellationDate;

    @Column
    private LocalDateTime cancellationTime;

    @Column(nullable = false)
    private String assigneeCompanyCode;

    @Column(nullable = false)
    private int assigneeId;

    @OneToMany(mappedBy = "collateralEntity", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<WithdrawReserveEntity> withdrawReserve;

    @OneToMany(mappedBy = "collateralEntity", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ReleaseCollateralEntity> releaseCollaterals;

    public boolean nowActive() {
        return this.getConfirmationDate() != null && this.getCancellationDate() == null;
    }

    public boolean canceled() {
        return this.getCancellationDate() != null;
    }

    public BigDecimal getRemainedAmount() {
        if (amount == null) return null;

        BigDecimal subAmount = BigDecimal.ZERO;
        if (releaseCollaterals != null)
            for (ReleaseCollateralEntity releaseCollateralEntity : releaseCollaterals)
                subAmount = subAmount.add(releaseCollateralEntity.getAmount());
        if (withdrawReserve != null)
            for (WithdrawReserveEntity withdrawReserveEntity : withdrawReserve)
                subAmount = subAmount.add(withdrawReserveEntity.getAmount());

        return amount.subtract(subAmount);
    }
}
