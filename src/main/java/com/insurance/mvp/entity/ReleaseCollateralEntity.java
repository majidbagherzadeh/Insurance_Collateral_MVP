package com.insurance.mvp.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "release_collateral")
@Data
public class ReleaseCollateralEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private BigDecimal amount;

    @JoinColumn
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private CollateralEntity collateralEntity;
}
