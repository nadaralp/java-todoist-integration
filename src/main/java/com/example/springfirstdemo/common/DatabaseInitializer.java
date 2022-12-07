package com.example.springfirstdemo.common;

import com.example.springfirstdemo.todoist.TodoistConfigProperties;
import com.example.springfirstdemo.todoist.UserTodoistInfo;
import com.example.springfirstdemo.todos.Todo;
import com.example.springfirstdemo.todos.TodoRepository;
import com.example.springfirstdemo.user.AppUser;
import com.example.springfirstdemo.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

@Configuration
@Slf4j
public class DatabaseInitializer {

    @Bean
    CommandLineRunner databaseInitialization(
            @Value("${test_param}") String testParam,
            @Value("${spring.jpa.hibernate.ddl-auto}") String ddlConfig,
            TodoistConfigProperties todoistConfigProperties,
            TodoRepository todoRepository,
            UserRepository userRepository
    ) {
        return args -> {
            log.info("custom params. test_param:{}, nadar.param:{}", testParam, null);

            if (ddlConfig.equals("create")) {
                log.info("seeding database");
                List<AppUser> seedUsers = getUserSeedWithTodos(todoistConfigProperties);
                seedUsers.forEach(user -> userRepository.saveAll(seedUsers));
            } else {
                log.info("not seeding because ddl config is: {}", ddlConfig);
            }

        };
    }

    List<AppUser> getUserSeedWithTodos(TodoistConfigProperties todoistConfigProperties) {
        AppUser nadar = new AppUser("Nadar Alpenidze", "nadar@gmail.com", LocalDate.of(1997, 10, 16));
        nadar.setTodos(
                List.of(
                        new Todo("Cook Meal", "Cook meal for my babe daria", false, null, nadar),
                        new Todo("Make bed", null, false, null, nadar)
                )
        );


        nadar.setTodoistInfo(
                new UserTodoistInfo(
                        todoistConfigProperties.getTodoistDevApiKey(), // receive as a parameter from application.properties
                        nadar
                )
        );

        AppUser daria = new AppUser("Daria Liukin", "darialiukin3@gmail.com", LocalDate.of(1998, 2, 3));
        daria.setTodos(
                List.of(
                        new Todo("Work", "DriveNets work", true, OffsetDateTime.now().plusDays(7), daria)
                )
        );
        return List.of(
                nadar,
                daria,
                new AppUser("David Korochik", "david@gmail.com", LocalDate.of(2001, 8, 15)),
                new AppUser("Alon Albahari", "alon@gmail.com", LocalDate.of(1994, 9, 23))
        );
    }
}
