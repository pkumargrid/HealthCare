package com.healthcare.system.mappers;

import com.healthcare.system.controllers.dto.HealthProviderDTO;
import com.healthcare.system.entities.HealthProvider;
import com.healthcare.system.exceptions.ValidationException;

public class HealthProviderMapper {

    public static HealthProvider mapToDomain(HealthProviderDTO healthProviderDTO)
            throws ValidationException {
        HealthProvider healthProvider = new HealthProvider();
        healthProvider.setName(healthProviderDTO.getName());
        healthProvider.setPassword(healthProviderDTO.getPassword());
        healthProvider.setEmail(healthProviderDTO.getEmail());
        return healthProvider;
    }
}
