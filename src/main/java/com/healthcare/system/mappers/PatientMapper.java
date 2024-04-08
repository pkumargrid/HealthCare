package com.healthcare.system.mappers;

import com.healthcare.system.controllers.dto.DoctorDTO;
import com.healthcare.system.controllers.dto.PatientDTO;
import com.healthcare.system.entities.Doctor;
import com.healthcare.system.entities.Patient;
import com.healthcare.system.exceptions.ValidationException;
import com.healthcare.system.repositories.HealthProviderRepository;

public class PatientMapper {

    public static Patient mapToDomain(PatientDTO patientDTO)
            throws ValidationException {
        Patient patient = new Patient();
        patient.setName(patientDTO.getName());
        patient.setPassword(patientDTO.getPassword());
        patient.setEmail(patientDTO.getEmail());

        return patient;
    }
}
