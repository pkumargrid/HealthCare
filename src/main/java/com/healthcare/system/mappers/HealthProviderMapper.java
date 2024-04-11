package com.healthcare.system.mappers;

import com.healthcare.system.dto.HealthProviderDTO;
import com.healthcare.system.entities.HealthProvider;

public class HealthProviderMapper {

    public static HealthProvider mapToDomain(HealthProviderDTO healthProviderDTO) {
        HealthProvider healthProvider = new HealthProvider();
        healthProvider.setName(healthProviderDTO.getName());
        healthProvider.setPassword(healthProviderDTO.getPassword());
        healthProvider.setEmail(healthProviderDTO.getEmail());
        return healthProvider;
    }
}
