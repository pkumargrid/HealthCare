package com.healthcare.system.controllers;


import com.healthcare.system.entities.Complaint;
import com.healthcare.system.entities.Doctor;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class AlongWithJpaSavingChildrenWithParent {

    @Autowired
    private EntityManager entityManager;
    @Test
    void testSaveParentWithChildren() {
        Doctor doctor = Doctor.builder().id(1).name("Pratik Kumar").complaintList(new ArrayList<>()).build();
        Complaint complaint = Complaint.builder().text("hello").tableName("doctor").type(1).build();
        doctor.getComplaintList().add(complaint);
        entityManager.unwrap(Session.class).save(doctor);
        Complaint complaint1 = entityManager.createQuery("select c from Complaint c", Complaint.class).getSingleResult();
        assertNotNull(complaint1.getId());
    }

    @Test
    void testMergeParentWithChildren() {
        Doctor doctor = Doctor.builder().id(1).name("Pratik Kumar").complaintList(new ArrayList<>()).build();
        Complaint complaint = Complaint.builder().id(1).text("hello").tableName("doctor").type(1).build();
        doctor.getComplaintList().add(complaint);
        entityManager.merge(doctor);
        Complaint complaint1 = entityManager.createQuery("select c from Complaint c", Complaint.class).getSingleResult();
        assertNotNull(complaint1.getId());
    }
}
