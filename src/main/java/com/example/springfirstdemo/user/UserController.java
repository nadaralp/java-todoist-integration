package com.example.springfirstdemo.user;

import com.example.springfirstdemo.common.exceptions.BadRequestException;
import com.example.springfirstdemo.common.exceptions.NotFoundException;
import com.example.springfirstdemo.user.api_models.AppUserDto;
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
import java.util.*;

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
        var usersResponse = users.map(user -> modelMapper.map(user, AppUserDto.class));
        return ResponseEntity.ok(usersResponse);
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
