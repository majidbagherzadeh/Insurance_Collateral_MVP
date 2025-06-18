package com.insurance.mvp.exceptions;

import java.time.LocalDateTime;

public class CollateralExpireException extends RuntimeException {
    public CollateralExpireException(LocalDateTime expireEnd) {
        super("زمان پایان بیمه استعلام گرفته شده " + expireEnd + "است که قبل از پایان زمان اتمام وثیقه درخواستی است");
    }
}
