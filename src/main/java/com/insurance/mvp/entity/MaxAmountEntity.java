package com.insurance.mvp.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "max_amount")
@Data
@NoArgsConstructor
public class MaxAmountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String nationalCode;

    @Column(nullable = false)
    private String ciiNumber;

    @Column(nullable = false)
    private String assigneeCompanyCode;

    @Column(nullable = false)
    private BigDecimal maxAmount;

    @Column(nullable = false)
    private LocalDateTime endDate;

    @Column
    private LocalDateTime lastInquiryDate;

    public MaxAmountEntity(MockInsuranceEntity mockInsuranceEntity) {
        nationalCode = mockInsuranceEntity.getNationalCode();
        ciiNumber = mockInsuranceEntity.getCiiNumber();
        assigneeCompanyCode = mockInsuranceEntity.getAssigneeCompanyCode();
        maxAmount = mockInsuranceEntity.getMaxAmount();
        endDate = LocalDateTime.now();
    }
}
