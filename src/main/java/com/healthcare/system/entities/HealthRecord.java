package com.healthcare.system.entities;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class HealthRecord {
    private int id;
    private HealthProvider healthProvider;
    private Doctor doctor;
    private Patient patient;
    private String prescription;
    private Report report;

}
