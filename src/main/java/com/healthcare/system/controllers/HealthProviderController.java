package com.healthcare.system.controllers;

import com.healthcare.system.controllers.dto.HealthProviderDTO;
import com.healthcare.system.controllers.dto.ResponseCrudDTO;
import com.healthcare.system.exceptions.ValidationException;
import com.healthcare.system.services.HealthProviderService;

public class HealthProviderController {
    private final HealthProviderService healthProviderService;

    public HealthProviderController(HealthProviderService healthProviderService) {
        this.healthProviderService = healthProviderService;
    }
    public ResponseCrudDTO register(HealthProviderDTO healthProviderDTO) {
        try{
            healthProviderService.register(healthProviderDTO);
            return new ResponseCrudDTO("Saved HealthProvider Successfully!");
        } catch (ValidationException v) {
            return new ResponseCrudDTO("Failed to Save!\n Reason: " + v.getMessage());
        }
    }
}
