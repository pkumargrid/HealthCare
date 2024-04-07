package com.healthcare.system.mappers;

import com.healthcare.system.controllers.dto.DoctorDTO;
import com.healthcare.system.entities.Doctor;
import com.healthcare.system.exceptions.ValidationException;
import com.healthcare.system.repositories.*;

public class DoctorMapper {

    public static Doctor mapToDomain(DoctorDTO doctorDTO,
                                     HealthProviderRepository healthProviderRepository)
            throws ValidationException {
        Doctor doctor = new Doctor();
        doctor.setName(doctorDTO.getName());
        doctor.setPassword(doctorDTO.getPassword());
        doctor.setEmail(doctorDTO.getEmail());
        doctor.setHealthProvider(healthProviderRepository.getById(doctorDTO.getHealthProviderId()));
        if (doctor.getHealthProvider() == null) {
            throw new ValidationException("Provided id for HealthProvider is wrong");
        }

        return doctor;
    }

}
