package com.healthcare.system.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Setter
@Getter
@Entity
public class Appointment {

    private boolean status;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Integer id;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinColumn(name = "doctor_id")
    @JsonBackReference
    private Doctor doctor;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinColumn(name = "patient_id")
    @JsonBackReference
    private Patient patient;

}
