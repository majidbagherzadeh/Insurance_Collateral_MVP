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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String ciiNumber;

    @Column
    private String nationalCode;

    @Column
    private short period;

    @Column
    private BigDecimal maxAmount;

    @Column
    private BigDecimal amount;

    @Column
    private LocalDateTime beginDate;

    @Column
    private LocalDateTime endDate;

    @Column
    private LocalDateTime creationTime;

    @Column
    private LocalDateTime confirmationDate;

    @Column
    private LocalDateTime confirmationTime;

    @Column
    private LocalDateTime cancellationDate;

    @Column
    private LocalDateTime cancellationTime;

    @Column
    private String assigneeCompanyCode;

    @Column
    private int assigneeId;

    @Column
    private boolean withdrawReserve;

    @OneToMany(mappedBy = "collateralEntity", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ReleaseCollateralEntity> releaseCollaterals;

    public boolean nowActive() {
        return this.getConfirmationDate() != null && this.getCancellationDate() == null && !this.isWithdrawReserve();
//            && (this.getEndDate() == null || this.getEndDate().isAfter(DateConverterUtil.toLocalDateTime(new Date())));
    }

    public boolean canceled() {
        return this.getCancellationDate() != null;
    }

    public BigDecimal calculateRemainedAmount() {
        BigDecimal withdrawAmount = BigDecimal.ZERO;
        for (ReleaseCollateralEntity releaseCollateralEntity : releaseCollaterals)
            withdrawAmount = withdrawAmount.add(releaseCollateralEntity.getAmount());

        return amount.subtract(withdrawAmount);
    }
}
