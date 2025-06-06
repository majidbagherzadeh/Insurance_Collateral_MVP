package com.insurance.mvp.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "insurance_collateral")
public class InsuranceCollateral {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "national_code")
    private String nationalCode;
    @Column(name = "cii_number")
    private String cIINumber;
    @Column(name = "assignee_company_code")
    private String assigneeCompanyCode;

    @Column(name = "max_amount")
    private BigDecimal maxAmount;
    @Column(name = "amount")
    private BigDecimal amount;
}
