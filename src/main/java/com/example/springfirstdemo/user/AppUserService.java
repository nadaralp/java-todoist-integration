package com.example.springfirstdemo.user;

import com.example.springfirstdemo.common.exceptions.BadRequestException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
public class AppUserService {
    private static final Logger logger = LoggerFactory.getLogger(AppUserService.class);

    private final UserRepository userRepository;

    @Autowired
    public AppUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public AppUser createUser(CreateUserRequest createUserRequest) {
        logger.info("Creating a new with the details: {}", createUserRequest);

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
}
