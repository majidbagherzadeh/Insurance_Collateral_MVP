package com.insurance.mvp.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table
@Data
public class MockInsuranceCollateral {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String nationalCode;
    @Column
    private String ciiNumber;
    @Column
    private String assigneeCompanyCode;

    @Column
    private int assigneeId;
    @Column
    private BigDecimal maxAmount;
}
