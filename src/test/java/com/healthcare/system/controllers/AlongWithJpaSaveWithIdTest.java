package com.healthcare.system.controllers;

import com.healthcare.system.entities.Doctor;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertThrows;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AlongWithJpaSaveWithIdTest {

    @Autowired
    private EntityManager entityManager;

    @Test
    void testPersistenceWithId() {
//        System.out.println(entityManager.createQuery("select d from Doctor d", Doctor.class).getResultList());
        Doctor doctor = Doctor.builder().id(1).name("Pratik Kumar").build();
        assertThrows(EntityExistsException.class, ()-> entityManager.persist(doctor));
    }

    @Test
    void testSaveWithId() {
        Doctor doctor = Doctor.builder().id(1).name("Pratik Kumar").build();
        entityManager.unwrap(Session.class).save(doctor);
    }

    @Test
    void testMergeWithId() {
        Doctor doctor = Doctor.builder().id(1).name("Pratik Kumar").build();
        entityManager.merge(doctor);
    }
}
