package com.healthcare.system.entities;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Setter
@Getter
public class Appointment {

    private  int id;
    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Doctor doctor;

    private Patient patient;
    private boolean status;

}
