package com.bank.entity;

import com.bank.validation.annotation.DecimalNonZero;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(
    name="movimiento",
    indexes={@Index(name="FECHA_INDICE", columnList="fecha")}
)
@Validated
public class Movement {
    @Column(name="id", nullable=false)
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(name="fecha", nullable=false, columnDefinition="DATE")
    @NotNull
    private LocalDate date;
    @Column(name="tipo", nullable=false, length=2)
    @NotNull
    @Enumerated(EnumType.STRING)
    private Type type;
    @Column(name="valor", nullable=false, precision=14, scale=2)
    @NotNull
    @DecimalNonZero
    private BigDecimal value;
    @Column(name="saldo", nullable=false, precision=14, scale=2)
    @NotNull
    private BigDecimal balance;
    @JoinColumn(name="cuenta_id", referencedColumnName="id", nullable=false, foreignKey=@ForeignKey(name="CF_MOVIMIENTO_A_CUENTA"))
    @OneToOne
    private Account account;

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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public enum Type {
        D, C
    }
}
