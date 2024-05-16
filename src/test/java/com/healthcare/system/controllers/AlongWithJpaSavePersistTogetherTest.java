package com.healthcare.system.controllers;

import com.healthcare.system.entities.Doctor;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
public class AlongWithJpaSavePersistTogetherTest {
    @Autowired
    private EntityManager entityManager;

    @Test
    void testSavePersist() {
        Doctor doctor = Doctor.builder().id(1).name("Pratik Kumar").build();
        int id = (Integer) entityManager.unwrap(Session.class).save(doctor);
        Doctor newDoctor = Doctor.builder().id(id).name("Ansh Makkar").build();
        assertThrows(EntityExistsException.class, () -> entityManager.persist(newDoctor));
    }
    @Test
    void testSaveMerge() {
        Doctor doctor = Doctor.builder().name("Pratik Kumar").build();
        int id = (Integer) entityManager.unwrap(Session.class).save(doctor);
        Doctor newDoctor = Doctor.builder().id(id).name("Ansh Makkar").build();
        entityManager.merge(newDoctor);
        List<Doctor> savedDoctors = entityManager.createQuery("select d from Doctor d", Doctor.class).getResultList();
        assertEquals(1, savedDoctors.size());
        assertEquals("Ansh Makkar", savedDoctors.get(0).getName());
    }
    @Test
    void testSaveWithSave() {
        Doctor doctor = Doctor.builder().id(1).name("Pratik Kumar").build();
        int id = (Integer) entityManager.unwrap(Session.class).save(doctor);
        Doctor newDoctor = Doctor.builder().id(id).name("Ansh Makkar").build();
        entityManager.unwrap(Session.class).save(newDoctor);
    }
}
