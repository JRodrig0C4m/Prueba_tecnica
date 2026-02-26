package com.example.juanrodrigo_camachoperez.controller;

import com.example.juanrodrigo_camachoperez.dto.request.CreateUserRequest;
import com.example.juanrodrigo_camachoperez.dto.request.PatchUserRequest;
import com.example.juanrodrigo_camachoperez.dto.response.UserResponse;
import com.example.juanrodrigo_camachoperez.service.UserService;
import com.example.juanrodrigo_camachoperez.util.UserMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    // GET /users?sortedBy=... OR /users?filter=...
    @GetMapping
    public List<UserResponse> list(@RequestParam(required = false) String sortedBy,
                                  @RequestParam(required = false) String filter) {
        return service.list(sortedBy, filter).stream().map(UserMapper::toResponse).toList();
    }

    @GetMapping("/{id}")
    public UserResponse get(@PathVariable UUID id) {
        return UserMapper.toResponse(service.get(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse create(@Valid @RequestBody CreateUserRequest req) {
        return UserMapper.toResponse(service.create(req));
    }

    @PatchMapping("/{id}")
    public UserResponse patch(@PathVariable UUID id, @Valid @RequestBody PatchUserRequest req) {
        return UserMapper.toResponse(service.patch(id, req));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
