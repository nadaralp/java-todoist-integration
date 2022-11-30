package com.example.springfirstdemo.user;

import com.example.springfirstdemo.common.exceptions.BadRequestException;
import com.example.springfirstdemo.common.exceptions.NotFoundException;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController()
@RequestMapping(value = "api/users")
public class UserController {

    private final AppUserService appUserService;

    @Autowired
    public UserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    //TODO: add optional queries with query string parameters
    @GetMapping
    public List<AppUser> getAll() {
        return appUserService.getAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getAll(@PathVariable UUID id) {
        Optional<AppUser> user = appUserService.getById(id);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user.get());
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody CreateUserRequest createUserRequest) {
        URI serverBaseUri = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUri();
        try {
            AppUser createdUser = appUserService.create(createUserRequest);
            return ResponseEntity
                    .created(
                            URI.create(serverBaseUri + "/api/users/" + createdUser.getId())
                    )
                    .header("x-test", "nadar")
                    .body(createdUser);
        } catch (BadRequestException e) {
            return ResponseEntity
                    .badRequest()
                    .body(
                            new HashMap<>() {{
                                put("status", BAD_REQUEST);
                                put("message", e.getMessage());
                            }}
                    );
        } catch (Exception e) {
            throw e;
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateUser(@PathVariable UUID id, @RequestBody UpdateUserRequest updateUserRequest) {
        try {
            AppUser updatedUser = appUserService.update(id, updateUserRequest);
            return ResponseEntity.ok(updatedUser);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteUser(@PathVariable UUID id) {
        try {
            appUserService.delete(id);
            return ResponseEntity.ok().build();
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }



}
