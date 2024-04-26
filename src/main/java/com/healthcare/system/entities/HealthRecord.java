package com.healthcare.system.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class HealthRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private HealthProvider healthProvider;
    private Doctor doctor;
    private Patient patient;
    private Prescription prescription;
    private Report report;

}
