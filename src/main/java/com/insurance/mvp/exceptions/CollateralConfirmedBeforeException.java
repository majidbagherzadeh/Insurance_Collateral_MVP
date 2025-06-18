package com.insurance.mvp.exceptions;

public class CollateralConfirmedBeforeException extends RuntimeException {
    public CollateralConfirmedBeforeException() {
        super("collaterals.collateralConfirmedBeforeException");
    }
}
