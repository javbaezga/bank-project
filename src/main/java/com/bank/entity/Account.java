package com.bank.entity;

import com.bank.validation.annotation.AccountNumber;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(
    name="cuenta",
    indexes={
        @Index(name="NUMERO_INDICE", columnList="numero", unique=true),
        @Index(name="FECHA_RESETEO_SALDO_DIARIO_INDICE", columnList="fecha_reseteo_saldo_diario")
    }
)
@Validated
public class Account {
    @Column(name="id", nullable=false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="numero", nullable=false, length=6)
    @NotBlank
    @AccountNumber
    private String number;
    @Column(name="tipo", nullable=false, length=2)
    @NotNull
    @Enumerated(EnumType.STRING)
    private Type type;
    @Column(name="saldo_inicial", nullable=false, precision=14, scale=2)
    @NotNull
    private BigDecimal initialBalance;
    @Column(name="estado", nullable=false, columnDefinition="TINYINT(1)")
    @NotNull
    private Boolean status;
    @Column(name="saldo", precision=14, scale=2)
    private BigDecimal balance;
    @Column(name="saldo_diario", precision=9, scale=2)
    private BigDecimal dailyBalance;
    @Column(name="fecha_reseteo_saldo_diario", columnDefinition="DATE")
    private LocalDate dailyBalanceResetDate;
    @JoinColumn(name="cliente_id", referencedColumnName="id", nullable=false, foreignKey=@ForeignKey(name="CF_CUENTA_A_CLIENTE"))
    @OneToOne
    private Client client;

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

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getDailyBalance() {
        return dailyBalance;
    }

    public void setDailyBalance(BigDecimal dailyBalance) {
        this.dailyBalance = dailyBalance;
    }

    public LocalDate getDailyBalanceResetDate() {
        return dailyBalanceResetDate;
    }

    public void setDailyBalanceResetDate(LocalDate dailyBalanceResetDate) {
        this.dailyBalanceResetDate = dailyBalanceResetDate;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public enum Type {
        A, C
    }
}
