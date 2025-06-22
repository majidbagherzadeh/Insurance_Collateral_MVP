package com.insurance.mvp.exceptions;

public class CollateralCanceledBeforeException extends RuntimeException {
    public CollateralCanceledBeforeException() {
        super("وثیقه قبلا کنسل شده است");
    }
}
