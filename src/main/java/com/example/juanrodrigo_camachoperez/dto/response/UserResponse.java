package com.example.juanrodrigo_camachoperez.dto.response;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserResponse {

    private UUID id;
    private String email;
    private String name;
    private String phone;
    private String taxId;
    private String createdAt;
    private List<AddressResponse> addresses = new ArrayList<>();

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getTaxId() { return taxId; }
    public void setTaxId(String taxId) { this.taxId = taxId; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public List<AddressResponse> getAddresses() { return addresses; }
    public void setAddresses(List<AddressResponse> addresses) { this.addresses = addresses; }
}
