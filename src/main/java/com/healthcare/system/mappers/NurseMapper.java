package com.healthcare.system.mappers;

import com.healthcare.system.dto.NurseDTO;
import com.healthcare.system.entities.Nurse;
import com.healthcare.system.exceptions.ValidationException;
import com.healthcare.system.exceptions.WrongCredentials;
import com.healthcare.system.repositories.HealthProviderRepository;

import java.rmi.ServerException;

public class NurseMapper {

    public static Nurse mapToDomain(NurseDTO nurseDTO,
                                    HealthProviderRepository healthProviderRepository)
            throws ValidationException, ServerException, WrongCredentials {
        Nurse nurse = new Nurse();
        nurse.setName(nurseDTO.getName());
        nurse.setPassword(nurseDTO.getPassword());
        nurse.setEmail(nurseDTO.getEmail());
        nurse.setHealthProvider(healthProviderRepository.getById(nurseDTO.getHealthProviderId()));
        if (nurse.getHealthProvider() == null) {
            throw new ValidationException("Provided id for HealthProvider is wrong");
        }

        return nurse;
    }
}
