package com.bank.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BankStatementResponseDto {
    @JsonProperty("Movimiento Id")
    private Long id;
    @JsonProperty("Fecha")
    private LocalDate date;
    @JsonProperty("Cliente")
    private String clientFullName;
    @JsonProperty("Número Cuenta")
    private String accountNumber;
    @JsonProperty("Tipo")
    private AccountResponseDto.Type type;
    @JsonProperty("Saldo Inicial")
    private BigDecimal initialBalance;
    @JsonProperty("Estado")
    private Boolean status;
    @JsonProperty("Movimiento")
    private BigDecimal value;
    @JsonProperty("Saldo Disponible")
    private BigDecimal balance;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getClientFullName() {
        return clientFullName;
    }

    public void setClientFullName(String clientFullName) {
        this.clientFullName = clientFullName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public AccountResponseDto.Type getType() {
        return type;
    }

    public void setType(AccountResponseDto.Type type) {
        this.type = type;
    }

    public BigDecimal getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(BigDecimal initialBalance) {
        this.initialBalance = initialBalance;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
