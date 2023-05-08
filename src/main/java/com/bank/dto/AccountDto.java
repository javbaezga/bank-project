package com.bank.dto;

import com.bank.validation.annotation.AccountNumber;
import com.bank.validation.group.Create;
import com.bank.validation.group.Patch;
import com.bank.validation.group.Update;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class AccountDto {
    @NotNull(groups={Create.class, Update.class})
    @Min(value=1, groups={Create.class, Update.class, Patch.class})
    @JsonProperty("Cliente Id")
    private Long clientId;
    @NotBlank(groups={Create.class, Update.class})
    @AccountNumber(groups={Create.class, Update.class, Patch.class})
    @JsonProperty("Número Cuenta")
    private String number;
    @NotNull(groups={Create.class, Update.class})
    @JsonProperty("Tipo")
    private Type type;
    @NotNull(groups={Create.class, Update.class})
    @DecimalMin(value="-1000000.00", groups={Create.class, Update.class, Patch.class})
    @DecimalMax(value="1000000.00", groups={Create.class, Update.class, Patch.class})
    @Digits(integer=7, fraction=2, groups={Create.class, Update.class, Patch.class})
    @JsonProperty("Saldo Inicial")
    private BigDecimal initialBalance;
    @NotNull(groups={Create.class, Update.class})
    @JsonProperty("Estado")
    private Boolean status;

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
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

    public enum Type {
        @JsonProperty("A")
        Savings,
        @JsonProperty("C")
        Current
    }
}
