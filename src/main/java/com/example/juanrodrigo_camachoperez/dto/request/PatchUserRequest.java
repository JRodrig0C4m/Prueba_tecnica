package com.example.juanrodrigo_camachoperez.dto.request;

import com.example.juanrodrigo_camachoperez.validation.annotations.ValidAndresPhone;
import com.example.juanrodrigo_camachoperez.validation.annotations.ValidRfcTaxId;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;

import java.util.List;

public class PatchUserRequest {

    @Email
    private String email;

    private String name;

    @ValidAndresPhone
    private String phone;

    private String password;

    @ValidRfcTaxId
    private String taxId;

    @Valid
    private List<CreateAddressRequest> addresses;

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getTaxId() { return taxId; }
    public void setTaxId(String taxId) { this.taxId = taxId; }

    public List<CreateAddressRequest> getAddresses() { return addresses; }
    public void setAddresses(List<CreateAddressRequest> addresses) { this.addresses = addresses; }
}
