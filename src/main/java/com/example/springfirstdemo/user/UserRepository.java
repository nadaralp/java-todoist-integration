package com.example.springfirstdemo.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<AppUser, UUID> {
//    List<AppUser> findAll();

    // Derived method names have two main parts separated by the first By keyword
    // The first part — such as find — is the introducer, and the rest — such as ByName — is the criteria.
    // Spring Data JPA supports find, read, query, count and get
    // We can also use Distinct, First or Top to remove duplicates or limit our result set:

    AppUser findFirstByEmail(String email);
    boolean existsByEmail(String email);
}
