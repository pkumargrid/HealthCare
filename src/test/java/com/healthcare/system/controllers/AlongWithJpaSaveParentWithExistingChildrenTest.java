package com.healthcare.system.controllers;


import com.healthcare.system.entities.Complaint;
import com.healthcare.system.entities.Doctor;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class AlongWithJpaSaveParentWithExistingChildrenTest {
    @Autowired
    private EntityManager entityManager;
    @Test
    void testSaveParentWithChildren() {
        Doctor doctor = Doctor.builder().id(1).name("Pratik Kumar").complaintList(new ArrayList<>()).build();
        Complaint complaint = Complaint.builder().text("hello").tableName("doctor").type(1).build();
        doctor.getComplaintList().add(complaint);
        entityManager.unwrap(Session.class).save(doctor);
        int cid = entityManager.createQuery("select c from Complaint c", Complaint.class).getSingleResult().getId();
        Doctor doctor1 = Doctor.builder().id(1).name("Pratik Kumar").complaintList(new ArrayList<>()).build();
        Complaint complaint1 = Complaint.builder().id(cid).text("just checking").tableName("doctor").type(1).build();
        doctor1.getComplaintList().add(complaint1);
        entityManager.merge(doctor1);
        Doctor existingDoctor = entityManager.createQuery("select d from Doctor d", Doctor.class).getSingleResult();
        assertEquals(1, existingDoctor.getComplaintList().size());
        assertEquals(cid, existingDoctor.getComplaintList().get(0).getId());
    }

}
