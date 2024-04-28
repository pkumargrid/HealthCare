package com.healthcare.system.dto;

import com.healthcare.system.entities.Doctor;
import com.healthcare.system.entities.Patient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class AppointmentDTO {

    private boolean status;
    private  Integer id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Doctor doctorId;
    private Patient patientId;
}
