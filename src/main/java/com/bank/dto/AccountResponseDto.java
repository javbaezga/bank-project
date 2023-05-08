package com.bank.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class AccountResponseDto {
    @JsonProperty("Id")
    private Long id;
    @JsonProperty("Número Cuenta")
    private String number;
    @JsonProperty("Tipo")
    private Type type;
    @JsonProperty("Saldo Inicial")
    private BigDecimal initialBalance;
    @JsonProperty("Estado")
    private Boolean status;
    @JsonProperty("Cliente")
    private String clientFullName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
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

    public String getClientFullName() {
        return clientFullName;
    }

    public void setClientFullName(String clientFullName) {
        this.clientFullName = clientFullName;
    }

    public enum Type {
        @JsonProperty("Ahorros")
        Savings,
        @JsonProperty("Corriente")
        Current
    }
}
