package com.example.springfirstdemo.user;

import com.example.springfirstdemo.common.exceptions.BadRequestException;
import com.example.springfirstdemo.common.exceptions.NotFoundException;
import com.example.springfirstdemo.user.dto.AppUserResponse;
import com.example.springfirstdemo.user.dto.CreateUserRequest;
import com.example.springfirstdemo.user.dto.UpdateUserRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController()
@RequestMapping(value = "api/users")
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final AppUserService appUserService;

    private final ModelMapper modelMapper;

    @Autowired
    public UserController(
            AppUserService appUserService,
            ModelMapper modelMapper) {
        this.appUserService = appUserService;
        this.modelMapper = modelMapper;
    }

    //TODO: add optional queries with query string parameters
    @GetMapping
    public ResponseEntity<?> getAll(
//            @RequestParam Map<String, String> requestParams
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("pageSize") Optional<Integer> pageSize
    ) {
        logger.info("get all users with query params: {}, {}", page, pageSize);
        Pageable pageable = PageRequest.of(
                page.orElse(0),
                pageSize.orElse(5),
                Sort.by("id")
        );
        logger.info("get all users pageable request: {}", pageable);
        Page<AppUser> users = appUserService.getAll(pageable);

        // the object -> to the dto
        Page<AppUserResponse> usersResponse = users.map(user -> modelMapper.map(user, AppUserResponse.class));
        return ResponseEntity.ok(usersResponse);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getAll(@PathVariable UUID id) {
        Optional<AppUser> user = appUserService.getById(id);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(modelMapper.map(user.get(), AppUserResponse.class));
    }

    // Example of a swagger annotation
    @Operation(
            summary = "Create a new user",
            tags = {"test-tag", "user-controller"},
            description = "test description"
    )
    @ApiResponse(description = "test api response", responseCode = "201")
    @PostMapping
    public ResponseEntity<?> createUser(
            @RequestBody @Valid CreateUserRequest createUserRequest
    ) {
        URI serverBaseUri = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUri();
        try {
            AppUser createdUser = appUserService.create(createUserRequest);
            return ResponseEntity
                    .created(
                            URI.create(serverBaseUri + "/api/users/" + createdUser.getId())
                    )
                    .header("x-test", "nadar")
                    .body(
                            modelMapper.map(createdUser, AppUserResponse.class)
                    );
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


    // Note: @ExceptionHandler must be either in the controller or a @ControllerAdvice global exception handler
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    @ResponseStatus(BAD_REQUEST)
//    public Map<String, String> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
//        return exception
//                .getBindingResult()
//                .getFieldErrors()
//                .stream()
//                .collect(
//                        Collectors.toMap(
//                                fieldError -> fieldError.getField(),
//                                fieldError -> fieldError.getDefaultMessage()
//                        )
//                );
//    }
}
