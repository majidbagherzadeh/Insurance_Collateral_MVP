package com.insurance.mvp.exceptions;

public class InsuranceNotFoundException extends RuntimeException {
    public InsuranceNotFoundException() {
        super("اطلاعات بیمه وثیقه پیدا نشد");
    }
}
