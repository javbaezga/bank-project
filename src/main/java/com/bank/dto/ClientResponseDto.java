package com.bank.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ClientResponseDto {
    @JsonProperty("Id")
    private Long id;
    @JsonProperty("Nombres")
    private String fullName;
    @JsonProperty("Dirección")
    private String address;
    @JsonProperty("Teléfono")
    private String phone;
    @JsonProperty("Contraseña")
    private String password;
    @JsonProperty("Estado")
    private Boolean status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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
