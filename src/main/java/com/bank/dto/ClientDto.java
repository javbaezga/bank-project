package com.bank.dto;

import com.bank.validation.annotation.Age;
import com.bank.validation.annotation.IdNumber;
import com.bank.validation.annotation.Phone;
import com.bank.validation.group.Create;
import com.bank.validation.group.Patch;
import com.bank.validation.group.Update;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ClientDto {
    @NotEmpty(groups={Create.class, Update.class})
    @Size(min=1, max=100, groups={Create.class, Update.class, Patch.class})
    @JsonProperty("Nombres")
    private String fullName;
    @NotNull(groups={Create.class, Update.class})
    @JsonProperty("Género")
    private Gender gender;
    @NotNull(groups={Create.class, Update.class})
    @Age(groups={Create.class, Update.class, Patch.class})
    @JsonProperty("Edad")
    private Byte age;
    @NotEmpty(groups={Create.class, Update.class})
    @IdNumber(groups={Create.class, Update.class, Patch.class})
    @JsonProperty("Identificación")
    private String idNumber;
    @NotEmpty(groups={Create.class, Update.class})
    @Size(min=1, max=255, groups={Create.class, Update.class, Patch.class})
    @JsonProperty("Dirección")
    private String address;
    @NotEmpty(groups={Create.class, Update.class})
    @Phone(groups={Create.class, Update.class, Patch.class})
    @JsonProperty("Teléfono")
    private String phone;
    @NotEmpty(groups={Create.class, Update.class})
    @Size(min=8, max=25, groups={Create.class, Update.class, Patch.class})
    @JsonProperty("Usuario")
    private String username;
    @NotEmpty(groups={Create.class, Update.class})
    @Size(min=8, max=50, groups={Create.class, Update.class, Patch.class})
    @JsonProperty("Contraseña")
    private String password;
    @NotNull(groups={Create.class, Update.class})
    @JsonProperty("Estado")
    private Boolean status;

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

    public enum Gender {
        @JsonProperty("M")
        Male,
        @JsonProperty("F")
        Female
    }
}
