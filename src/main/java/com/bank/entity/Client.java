package com.bank.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

@Entity
@Table(
    name="cliente",
    uniqueConstraints={@UniqueConstraint(name="USUARIO_INDICE", columnNames={"usuario"})}
)
@PrimaryKeyJoinColumn(foreignKey=@ForeignKey(name="CF_CLIENTE_A_PERSONA"))
@Validated
public class Client extends Person {
    @Column(name="usuario", nullable=false, length=25)
    @NotBlank
    @Size(min=8, max=25)
    private String username;
    @Column(name="contrasena", nullable=false, length=50)
    @NotBlank
    @Size(min=8, max=50)
    private String password;
    @Column(name="estado", nullable=false, columnDefinition="TINYINT(1)")
    @NotNull
    private Boolean status;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
