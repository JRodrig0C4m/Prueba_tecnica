package com.example.juanrodrigo_camachoperez.model;

import jakarta.persistence.*;

@Entity
@Table(
        name = "addresses",
        uniqueConstraints = @UniqueConstraint(name = "uk_user_addrid", columnNames = {"user_id", "addr_id"})
)
public class Address {

    // internal DB PK (not exposed)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk", nullable = false)
    private Long pk;

    // exposed id per PDF
    @Column(name = "addr_id", nullable = false)
    private Long addressId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "country_code", nullable = false)
    private String countryCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Address() {}

    public Address(Long addressId, String name, String street, String countryCode) {
        this.addressId = addressId;
        this.name = name;
        this.street = street;
        this.countryCode = countryCode;
    }

    public Long getPk() { return pk; }
    public void setPk(Long pk) { this.pk = pk; }

    public Long getAddressId() { return addressId; }
    public void setAddressId(Long addressId) { this.addressId = addressId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }

    public String getCountryCode() { return countryCode; }
    public void setCountryCode(String countryCode) { this.countryCode = countryCode; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
