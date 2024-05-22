package com.healthcare.system.controllers;


import com.healthcare.system.entities.Complaint;
import com.healthcare.system.entities.Patient;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class AlongWithJpaSaveChild {
    @Autowired
    private EntityManager entityManager;

    @AfterEach
    @Modifying
    public void cleanUp() {
        int deletedCount1 = entityManager.createQuery("delete from Complaint").executeUpdate();
        int deletedCount2 = entityManager.createQuery("delete from Patient").executeUpdate();
    }
    @Test
    void testSaveChildWithoutParent() {
        Complaint complaint = Complaint.builder().text("hello").tableName("doctor").type(1).build();
        entityManager.unwrap(Session.class).save(complaint);
    }

    @Test
    void testPersistChildWithoutParent() {
        Complaint complaint = Complaint.builder().text("hello").tableName("doctor").type(1).build();
        entityManager.persist(complaint);
    }

    @Test
    void testMergeChildWithoutParent() {
        Complaint complaint = Complaint.builder().text("hello").tableName("doctor").type(1).build();
        entityManager.merge(complaint);
    }

    @Test
    void testPersistChildWithParentNotPresentInDatabase() {
        Complaint complaint = Complaint.builder().text("hello").tableName("doctor").type(1).build();
        Patient patient = Patient.builder().name("Ansh Makkar").email("pat@gmail.com").build();
        complaint.setPatient(patient);
        entityManager.persist(complaint);
        List<Patient> patients = entityManager.createQuery("select p from Patient p", Patient.class).getResultList();
        assertEquals(1, patients.size());
        assertEquals("Ansh Makkar", patients.get(0).getName());
    }

    @Test
    void testPersistChildWithParentPresentInDatabase() {
        Complaint complaint = Complaint.builder().text("hello").tableName("doctor").type(1).build();
        Patient patient = Patient.builder().name("Ansh Makkar").email("pat@gmail.com").build();
        entityManager.persist(patient);
        complaint.setPatient(patient);
        entityManager.persist(complaint);
        List<Patient> patients = entityManager.createQuery("select p from Patient p", Patient.class).getResultList();
        assertEquals(1, patients.size());
        assertEquals("Ansh Makkar", patients.get(0).getName());
    }

    @Test
    void testMergeChildWithParentNotPresentInDatabase() {
        Complaint complaint = Complaint.builder().id(1).text("hello").tableName("doctor").type(1).build();
        Patient patient = Patient.builder().id(1).name("Ansh Makkar").email("pat@gmail.com").build();
        complaint.setPatient(patient);
        entityManager.merge(complaint);
        List<Patient> patients = entityManager.createQuery("select p from Patient p", Patient.class).getResultList();
        assertEquals(1, patients.size());
        assertEquals("Ansh Makkar", patients.get(0).getName());
    }

    @Test
    @Transactional
    void testMergeChildWithParentPresentInDatabase() {
        Complaint complaint = Complaint.builder().id(1).text("hello").tableName("doctor").type(1).build();
        Patient patient = Patient.builder().id(1).name("Ansh Makkar").email("pat@gmail.com").build();
        Patient patient1 = entityManager.merge(patient);
        complaint.setPatient(patient1);
        entityManager.merge(complaint);
        List<Patient> patients = entityManager.createQuery("select p from Patient p", Patient.class).getResultList();
        patients.forEach(p -> System.out.println(p.getId() + " " + p.getName() + " " + p.getEmail()));
        assertEquals(1, patients.size());
        assertEquals("Ansh Makkar", patients.get(0).getName());
//        patients.forEach(p -> System.out.println(p.getId()));
    }
    @Test
    @Transactional
    void testSaveChildWithParentNotPresentInDatabase() {
        Complaint complaint = Complaint.builder().text("hello").tableName("doctor").type(1).build();
        Patient patient = Patient.builder().name("Ansh Makkar").email("pat@gmail.com").build();
        complaint.setPatient(patient);
        entityManager.unwrap(Session.class).save(complaint);
        List<Patient> patients = entityManager.createQuery("select p from Patient p", Patient.class).getResultList();
        assertEquals(1, patients.size());
        assertEquals("Ansh Makkar", patients.get(0).getName());
    }

    @Test
    @Transactional
    void testSaveChildWithParentPresentInDatabase() {
        Complaint complaint = Complaint.builder().text("hello").tableName("doctor").type(1).build();
        Patient patient = Patient.builder().name("Ansh Makkar").email("pat@gmail.com").build();
        entityManager.unwrap(Session.class).save(patient);
        complaint.setPatient(patient);
        entityManager.unwrap(Session.class).save(complaint);
        List<Patient> patients = entityManager.createQuery("select p from Patient p", Patient.class).getResultList();
        assertEquals(1, patients.size());
        assertEquals("Ansh Makkar", patients.get(0).getName());
    }

}
