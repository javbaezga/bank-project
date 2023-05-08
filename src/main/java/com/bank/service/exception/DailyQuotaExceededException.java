package com.bank.service.exception;

import java.math.BigDecimal;

public class DailyQuotaExceededException extends RuntimeException {
    private final BigDecimal dailyBalance;

    public DailyQuotaExceededException(final BigDecimal dailyBalance) {
        super("Daily quota exceeded");
        this.dailyBalance = dailyBalance;
    }

    public BigDecimal getDailyBalance() {
        return dailyBalance;
    }
}
