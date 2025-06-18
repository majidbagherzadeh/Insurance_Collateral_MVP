package com.insurance.mvp.exceptions;

public class CollateralNotFoundException extends RuntimeException {
    public CollateralNotFoundException() {
        super("وثیقه پیدا نشد");
    }
}
