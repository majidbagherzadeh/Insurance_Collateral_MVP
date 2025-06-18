package com.insurance.mvp.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "mock_insurance")
@Data
public class MockInsuranceEntity {
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
    private int assigneeId;

    @Column(nullable = false)
    private BigDecimal maxAmount;

    @Column(nullable = false)
    private LocalDateTime endDate;
}
