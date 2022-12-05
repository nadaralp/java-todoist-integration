package com.example.springfirstdemo.common;

import com.example.springfirstdemo.todos.Todo;
import com.example.springfirstdemo.todos.TodoRepository;
import com.example.springfirstdemo.user.AppUser;
import com.example.springfirstdemo.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

@Configuration
public class DatabaseInitializer {

    @Bean
    CommandLineRunner databaseInitialization(
            TodoRepository todoRepository,
            UserRepository userRepository
    ) {
        return args -> {
            List<AppUser> seedUsers = getUserSeedWithTodos();
            seedUsers.forEach(user -> userRepository.saveAll(seedUsers));
        };
    }

    List<AppUser> getUserSeedWithTodos() {
        AppUser nadar = new AppUser("Nadar Alpenidze", "nadar@gmail.com", LocalDate.of(1997, 10, 16));
        nadar.setTodos(
                List.of(
                        new Todo("Cook Meal", "Cook meal for my babe daria", false, null, nadar),
                        new Todo("Make bed", null, false, null, nadar)
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
