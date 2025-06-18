package com.insurance.mvp.exceptions;

public class CollateralMaxAmountException  extends RuntimeException {
    public CollateralMaxAmountException() {
        super("مبلغ مجموع وثایق روی این بیمه نامه بیشتر از حداکثر مبلغ مجاز است");
    }
}
