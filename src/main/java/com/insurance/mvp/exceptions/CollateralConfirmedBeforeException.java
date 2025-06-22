package com.insurance.mvp.exceptions;

public class CollateralConfirmedBeforeException extends RuntimeException {
    public CollateralConfirmedBeforeException() {
        super("وثیقه قبلا تایید شده است");
    }
}
