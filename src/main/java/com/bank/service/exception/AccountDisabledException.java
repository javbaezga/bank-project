package com.bank.service.exception;

import com.bank.dto.AccountResponseDto;

public class AccountDisabledException extends RuntimeException {
    private final AccountResponseDto account;

    public AccountDisabledException(final AccountResponseDto account) {
        super("Account disabled");
        this.account = account;
    }

    public AccountResponseDto getAccount() {
        return account;
    }
}
