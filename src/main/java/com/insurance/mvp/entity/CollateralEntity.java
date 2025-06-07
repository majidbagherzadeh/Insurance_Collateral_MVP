package com.insurance.mvp.entity;

import com.insurance.mvp.util.DateConverterUtil;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

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
    private BigDecimal remainedAmount;

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

    public boolean nowActive() {
        return this.getConfirmationDate() != null && this.getCancellationDate() == null &&
                (this.getEndDate() == null || this.getEndDate().isAfter(DateConverterUtil.toLocalDateTime(new Date())));
    }

    public boolean canceled() {
        return this.getCancellationDate() != null;
    }
}
