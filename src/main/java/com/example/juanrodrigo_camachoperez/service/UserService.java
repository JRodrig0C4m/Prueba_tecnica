package com.example.juanrodrigo_camachoperez.service;

import com.example.juanrodrigo_camachoperez.dto.request.CreateAddressRequest;
import com.example.juanrodrigo_camachoperez.dto.request.CreateUserRequest;
import com.example.juanrodrigo_camachoperez.dto.request.PatchUserRequest;
import com.example.juanrodrigo_camachoperez.exception.BadRequestException;
import com.example.juanrodrigo_camachoperez.exception.NotFoundException;
import com.example.juanrodrigo_camachoperez.model.Address;
import com.example.juanrodrigo_camachoperez.model.User;
import com.example.juanrodrigo_camachoperez.repository.UserRepository;
import com.example.juanrodrigo_camachoperez.util.CryptoUtil;
import com.example.juanrodrigo_camachoperez.util.DateTimeUtil;
import com.example.juanrodrigo_camachoperez.util.FilteringUtil;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    private static final Set<String> SORT_FIELDS = Set.of("email","id","name","phone","tax_id","created_at");

    private final UserRepository repo;
    private final CryptoUtil crypto;

    public UserService(UserRepository repo, CryptoUtil crypto) {
        this.repo = repo;
        this.crypto = crypto;
    }

    public List<User> list(String sortedBy, String filter) {
        List<User> users = repo.findAll();

        if (filter != null && !filter.isBlank()) {
            users = users.stream()
                    .filter(FilteringUtil.parseFilter(filter))
                    .collect(Collectors.toList());
        }

        if (sortedBy != null && !sortedBy.isBlank()) {
            String sb = sortedBy.trim().toLowerCase(Locale.ROOT);
            if (!SORT_FIELDS.contains(sb)) {
                throw new BadRequestException("Invalid sortedBy. Allowed: " + SORT_FIELDS);
            }
            users.sort(comparatorFor(sb));
        }

        return users;
    }

    public User get(UUID id) {
        return repo.findById(id).orElseThrow(() -> new NotFoundException("User not found: " + id));
    }

    public User create(CreateUserRequest req) {
        String taxUpper = req.getTaxId().trim().toUpperCase(Locale.ROOT);
        if (repo.existsByTaxIdIgnoreCase(taxUpper)) {
            throw new BadRequestException("tax_id should be unique");
        }

        User u = new User();
        u.setId(UUID.randomUUID());
        u.setEmail(req.getEmail().trim());
        u.setName(req.getName().trim());
        u.setPhone(req.getPhone().trim());
        u.setTaxId(taxUpper);
        u.setCreatedAt(DateTimeUtil.nowMadagascarLocalDateTime());
        u.setPasswordEncrypted(crypto.encrypt(req.getPassword()));
        u.setAddresses(mapAddresses(req.getAddresses()));

        try {
            return repo.save(u);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException("tax_id should be unique");
        }
    }

    public User patch(UUID id, PatchUserRequest req) {
        User u = get(id);

        if (req.getEmail() != null) u.setEmail(req.getEmail().trim());
        if (req.getName() != null) u.setName(req.getName().trim());
        if (req.getPhone() != null) u.setPhone(req.getPhone().trim());

        if (req.getTaxId() != null) {
            String newTax = req.getTaxId().trim().toUpperCase(Locale.ROOT);
            Optional<User> existing = repo.findByTaxIdIgnoreCase(newTax);
            if (existing.isPresent() && !existing.get().getId().equals(u.getId())) {
                throw new BadRequestException("tax_id should be unique");
            }
            u.setTaxId(newTax);
        }

        if (req.getPassword() != null) {
            u.setPasswordEncrypted(crypto.encrypt(req.getPassword()));
        }

        if (req.getAddresses() != null) {
            u.setAddresses(mapAddresses(req.getAddresses()));
        }

        try {
            return repo.save(u);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException("tax_id should be unique");
        }
    }

    public void delete(UUID id) {
        if (!repo.existsById(id)) {
            throw new NotFoundException("User not found: " + id);
        }
        repo.deleteById(id);
    }

    public User authenticate(String taxId, String password) {
        String taxUpper = taxId.trim().toUpperCase(Locale.ROOT);
        User u = repo.findByTaxIdIgnoreCase(taxUpper).orElseThrow(() -> new BadRequestException("Invalid credentials"));
        String plain = crypto.decrypt(u.getPasswordEncrypted());
        if (!plain.equals(password)) {
            throw new BadRequestException("Invalid credentials");
        }
        return u;
    }

    private static List<Address> mapAddresses(List<CreateAddressRequest> reqs) {
        if (reqs == null) return List.of();
        return reqs.stream()
                .map(r -> new Address(
                        r.getId(),
                        r.getName().trim(),
                        r.getStreet().trim(),
                        r.getCountryCode().trim()
                ))
                .collect(Collectors.toList());
    }

    private static Comparator<User> comparatorFor(String field) {
        return switch (field) {
            case "email" -> Comparator.comparing(u -> nullSafe(u.getEmail()));
            case "id" -> Comparator.comparing(u -> u.getId() == null ? "" : u.getId().toString());
            case "name" -> Comparator.comparing(u -> nullSafe(u.getName()));
            case "phone" -> Comparator.comparing(u -> nullSafe(u.getPhone()));
            case "tax_id" -> Comparator.comparing(u -> nullSafe(u.getTaxId()));
            case "created_at" -> Comparator.comparing(User::getCreatedAt, Comparator.nullsLast(Comparator.naturalOrder()));
            default -> throw new BadRequestException("Invalid sortedBy");
        };
    }

    private static String nullSafe(String s) {
        return (s == null) ? "" : s.toLowerCase(Locale.ROOT);
    }
}
