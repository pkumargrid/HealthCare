package com.healthcare.system.controllers;

import com.healthcare.system.dto.HealthProviderDTO;
import com.healthcare.system.dto.ResponseCrudDTO;
import com.healthcare.system.entities.HealthProvider;
import com.healthcare.system.exceptions.ValidationException;
import com.healthcare.system.exceptions.WrongCredentials;
import com.healthcare.system.services.HealthProviderService;

import java.rmi.ServerException;
import java.util.List;

public class HealthProviderController {
    private final HealthProviderService healthProviderService;

    public HealthProviderController(HealthProviderService healthProviderService) {
        this.healthProviderService = healthProviderService;
    }
    public ResponseCrudDTO<Void> register(HealthProviderDTO healthProviderDTO) {
        try{
            healthProviderService.register(healthProviderDTO);
            return new ResponseCrudDTO<Void>("Saved HealthProvider Successfully!", 201,null);
        } catch (ValidationException v) {
            return new ResponseCrudDTO<Void>("Failed to Save!\n Reason: " + v.getMessage(), 403,null);
        } catch (WrongCredentials w) {
            return new ResponseCrudDTO<Void>("Failed to Save!\n Reason: " + w.getMessage(), 401,null);
        } catch (ServerException e) {
            return new ResponseCrudDTO<Void>("Failed to Save!\n Reason: " + e.getMessage(), 500,null);
        }
    }

    public ResponseCrudDTO<List<HealthProvider>> findAll() {
        try{
            List<HealthProvider> healthProviders = healthProviderService.findAll();
            return new ResponseCrudDTO<>("Fetched HealthProviders Successfully!", 200, healthProviders);
        } catch (ServerException e) {
            return new ResponseCrudDTO<>("Failed to Retrieve!\n Reason: " + e.getMessage(), 500,null);
        } catch (WrongCredentials e) {
            return new ResponseCrudDTO<>("Failed to Retrieve!\n Reason: " + e.getMessage(), 401,null);
        }
    }
}
