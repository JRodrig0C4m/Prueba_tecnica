package com.example.juanrodrigo_camachoperez.util;

import com.example.juanrodrigo_camachoperez.dto.response.AddressResponse;
import com.example.juanrodrigo_camachoperez.dto.response.UserResponse;
import com.example.juanrodrigo_camachoperez.model.Address;
import com.example.juanrodrigo_camachoperez.model.User;

import java.util.stream.Collectors;

public final class UserMapper {
    private UserMapper() {}

    public static UserResponse toResponse(User u) {
        UserResponse r = new UserResponse();
        r.setId(u.getId());
        r.setEmail(u.getEmail());
        r.setName(u.getName());
        r.setPhone(u.getPhone());
        r.setTaxId(u.getTaxId());
        r.setCreatedAt(DateTimeUtil.formatMadagascar(u.getCreatedAt()));
        r.setAddresses(u.getAddresses().stream().map(UserMapper::toResponse).collect(Collectors.toList()));
        return r;
    }

    private static AddressResponse toResponse(Address a) {
        return new AddressResponse(a.getAddressId(), a.getName(), a.getStreet(), a.getCountryCode());
    }
}
