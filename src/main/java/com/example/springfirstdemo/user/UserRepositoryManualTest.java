package com.example.springfirstdemo.user;

import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserRepositoryManualTest {

    @PersistenceUnit
    private final EntityManagerFactory entityManagerFactory;

    @Autowired
    public UserRepositoryManualTest(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public List<AppUser> getAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
//        entityManager.getTransaction().begin();
//
//        entityManager.getTransaction().commit();

        TypedQuery<AppUser> query = entityManager.createQuery("select u from AppUser u", AppUser.class);
        List<AppUser> users = query.getResultList();

        System.out.println("Found users count: " + users.size());
        return users;
    }
}
