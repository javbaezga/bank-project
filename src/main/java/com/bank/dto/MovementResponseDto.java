package com.bank.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class MovementResponseDto {
    @JsonProperty("Id")
    private Long id;
    @JsonProperty("Número Cuenta")
    private String accountNumber;
    @JsonProperty("Tipo")
    private AccountResponseDto.Type type;
    @JsonProperty("Saldo Inicial")
    private BigDecimal initialBalance;
    @JsonProperty("Estado")
    private Boolean status;
    @JsonProperty("Movimiento")
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
