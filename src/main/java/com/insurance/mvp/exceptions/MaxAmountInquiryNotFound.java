package com.insurance.mvp.exceptions;

public class MaxAmountInquiryNotFound extends RuntimeException {
    public MaxAmountInquiryNotFound() {
        super("استعلام حداکثر مبلغ مجاز برای وثیقه گذاری پیدا نشد");
    }
}
