package com.example.juanrodrigo_camachoperez.controller;

import com.example.juanrodrigo_camachoperez.dto.request.LoginRequest;
import com.example.juanrodrigo_camachoperez.dto.response.UserResponse;
import com.example.juanrodrigo_camachoperez.exception.UnauthorizedException;
import com.example.juanrodrigo_camachoperez.service.UserService;
import com.example.juanrodrigo_camachoperez.util.UserMapper;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    private final UserService service;

    public AuthController(UserService service) {
        this.service = service;
    }

    // POST /login (tax_id as username)
    @PostMapping("/login")
    public UserResponse login(@Valid @RequestBody LoginRequest req) {
        try {
            return UserMapper.toResponse(service.authenticate(req.getTaxId(), req.getPassword()));
        } catch (Exception e) {
            throw new UnauthorizedException("Invalid credentials");
        }
    }
}
