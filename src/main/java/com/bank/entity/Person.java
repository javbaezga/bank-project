package com.bank.entity;

import com.bank.validation.annotation.Age;
import com.bank.validation.annotation.IdNumber;
import com.bank.validation.annotation.Phone;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

@Entity
@Table(
    name="persona",
    uniqueConstraints={@UniqueConstraint(name="IDENTIFICACION_INDICE", columnNames={"identificacion"})}
)
@Inheritance(strategy=InheritanceType.JOINED)
@Validated
public class Person {
    @Column(name="id", nullable=false)
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(name="nombres", nullable=false, length=100)
    @NotBlank
    @Size(max=100)
    private String fullName;
    @Column(name="genero", nullable=false, length=2)
    @NotNull
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(name="edad", nullable=false)
    @NotNull
    @Age
    private Byte age;
    @Column(name="identificacion", nullable=false, length=10)
    @NotBlank
    @IdNumber
    private String idNumber;
    @Column(name="direccion", nullable=false, length=255)
    @NotBlank
    @Size(max=255)
    private String address;
    @Column(name="telefono", nullable=false, length=10)
    @NotBlank
    @Phone
    private String phone;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id=id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Byte getAge() {
        return age;
    }

    public void setAge(Byte age) {
        this.age = age;
    }

    public void setAge(int age) {
        this.age = (byte) age;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public enum Gender {
        M, F
    }
}
