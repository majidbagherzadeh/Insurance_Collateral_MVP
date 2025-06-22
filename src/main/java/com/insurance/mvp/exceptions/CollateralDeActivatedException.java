package com.insurance.mvp.exceptions;

public class CollateralDeActivatedException extends RuntimeException {
    public CollateralDeActivatedException() {
        super("وثیقه فعال نیست");
    }
}
