package com.insurance.mvp.exceptions;

public class CollateralCanceledBeforeException extends RuntimeException {
    public CollateralCanceledBeforeException() {
        super("collaterals.collateralCanceledBeforeException");
    }
}
