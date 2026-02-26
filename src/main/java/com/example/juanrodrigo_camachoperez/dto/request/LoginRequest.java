package com.example.juanrodrigo_camachoperez.dto.request;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {

    @NotBlank
    private String taxId;

    @NotBlank
    private String password;

    public String getTaxId() { return taxId; }
    public void setTaxId(String taxId) { this.taxId = taxId; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
