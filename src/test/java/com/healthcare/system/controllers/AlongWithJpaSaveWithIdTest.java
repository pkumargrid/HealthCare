package com.healthcare.system.controllers;

import com.healthcare.system.entities.Doctor;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY, connection = EmbeddedDatabaseConnection.H2)
public class AlongWithJpaSaveWithIdTest {

    @Autowired
    private EntityManager entityManager;
    @Test
    void testPersistenceWithId() {
        Doctor doctor = Doctor.builder().name("Pratik Kumar").build();
        entityManager.persist(doctor);
    }

    @Test
    void testSaveWithId() {
        Doctor doctor = Doctor.builder().name("Pratik Kumar").build();
        entityManager.unwrap(Session.class).save(doctor);
    }

    @Test
    void testMergeWithId() {
        Doctor doctor = Doctor.builder().name("Pratik Kumar").build();
        entityManager.merge(doctor);
    }
}
