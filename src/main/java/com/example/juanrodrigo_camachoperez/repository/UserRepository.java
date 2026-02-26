package com.example.juanrodrigo_camachoperez.repository;

import com.example.juanrodrigo_camachoperez.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByTaxIdIgnoreCase(String taxId);
    boolean existsByTaxIdIgnoreCase(String taxId);
}
