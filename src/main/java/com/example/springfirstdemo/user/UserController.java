package com.example.springfirstdemo.user;

import com.example.springfirstdemo.common.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletPath;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController()
@RequestMapping(value = "api/users")
public class UserController {

    private final AppUserService appUserService;

    @Autowired
    public UserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

//    @GetMapping
//    public List<AppUser> getAll() {
//        return userRepository.getAll();
//    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody CreateUserRequest createUserRequest) {
        URI serverBaseUri = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUri();
        try {
            AppUser createdUser = appUserService.createUser(createUserRequest);
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
}
