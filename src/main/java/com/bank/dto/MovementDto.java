package com.bank.dto;

import com.bank.validation.annotation.AccountNumber;
import com.bank.validation.annotation.DecimalNonZero;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class MovementDto {
    @NotNull
    @AccountNumber
    @JsonProperty("Número Cuenta")
    private String accountNumber;
    @NotNull
    @DecimalNonZero
    @DecimalMin("-1000000.00")
    @DecimalMax("1000000.00")
    @Digits(integer=7, fraction=2)
    @JsonProperty("Valor")
    private BigDecimal value;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
