package com.example.springfirstdemo.user;

import com.example.springfirstdemo.common.exceptions.BadRequestException;
import com.example.springfirstdemo.common.exceptions.NotFoundException;
import jakarta.transaction.Transactional;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AppUserService {
    private static final Logger logger = LoggerFactory.getLogger(AppUserService.class);

    private final UserRepository userRepository;

    @Autowired
    public AppUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Page<AppUser> getAll(Pageable pageable) {
        logger.debug("getting all users");

        Page<AppUser> users = userRepository.findAll(pageable);
        return users;
    }

    public Optional<AppUser> getById(UUID userId) {
        logger.info("getting user by userId: {}", userId);

        Optional<AppUser> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            logger.info("user not found with userId: {}", userId);
        } else {
            logger.info("user found with userId: {}, email: {}", user.get().getId(), user.get().getEmail());
        }
        return user;

    }

    @Transactional()
    public AppUser create(CreateUserRequest createUserRequest) {
        logger.info("Creating a new with the details: {}", createUserRequest);

//        AppUser userWithSameEmail = userRepository.findFirstByEmail(createUserRequest.getEmail());
//        if(userWithSameEmail != null) {
//            throw new BadRequestException("email: %s is already taken", createUserRequest.getEmail());
//        }
        if (userRepository.existsByEmail(createUserRequest.getEmail())) {
            throw new BadRequestException("email: %s is already taken", createUserRequest.getEmail());
        }

        if (createUserRequest.getName() == null || createUserRequest.getName().isEmpty()) {
            throw new BadRequestException("Cannot create user. Empty user name received");
        }

        if (createUserRequest.getEmail() == null || createUserRequest.getEmail().isEmpty()) {
            throw new BadRequestException("Cannot create user. Empty user email received");
        }

        if (createUserRequest.getDateOfBirth() == null ||
                Period.between(createUserRequest.getDateOfBirth(), LocalDate.now()).getYears() < 18
        ) {
            throw new BadRequestException("User age must be bigger than 18");
        }

        AppUser newUser = new AppUser(
                createUserRequest.getName(),
                createUserRequest.getEmail(),
                createUserRequest.getDateOfBirth()
        );
        newUser = userRepository.save(newUser);
        logger.info("User created: {}", newUser);
        return newUser;
    }

    @Transactional
    public AppUser update(UUID userId, UpdateUserRequest updateUserRequest) {
        logger.debug("updated user by userId: {} with new data: {}", userId, updateUserRequest);
        Optional<AppUser> user = getById(userId);
        if (user.isEmpty()) {
            throw new NotFoundException("user with userId: %s not found", userId);
        }

        if (updateUserRequest.getName() != null && !updateUserRequest.getName().isEmpty()) {
            user.get().setName(updateUserRequest.getName());
        }

        if (updateUserRequest.getEmail() != null && !updateUserRequest.getEmail().isEmpty()) {
            user.get().setEmail(updateUserRequest.getEmail());
        }

        if (updateUserRequest.getDateOfBirth() != null) {
            user.get().setDateOfBirth(updateUserRequest.getDateOfBirth());
        }

        logger.info("user {} updated", userId);
//        userRepository.save(user.get());

        return user.get();
    }

    @Transactional
    public void delete(UUID userId) {
        logger.info("deleting user: {}", userId);

        userRepository.deleteById(userId);
        logger.info("user {} deleted", userId);
    }
}
