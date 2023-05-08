package com.bank.service.exception;

import java.math.BigDecimal;

public class NotAvailableBalanceException extends RuntimeException {
    private final BigDecimal balance;

    public NotAvailableBalanceException(final BigDecimal balance) {
        super("Balance not available");
        this.balance = balance;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
