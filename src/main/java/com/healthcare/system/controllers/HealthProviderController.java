package com.healthcare.system.controllers;

import com.healthcare.system.dto.HealthProviderDTO;
import com.healthcare.system.dto.ResponseCrudDTO;
import com.healthcare.system.entities.HealthProvider;
import com.healthcare.system.exceptions.ValidationException;
import com.healthcare.system.exceptions.WrongCredentials;
import com.healthcare.system.services.HealthProviderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/healthcare")
public class HealthProviderController {
    private final HealthProviderService healthProviderService;

    public HealthProviderController(HealthProviderService healthProviderService) {
        this.healthProviderService = healthProviderService;
    }

    @PostMapping("/")
    public ResponseCrudDTO<Void> register(@RequestBody HealthProviderDTO healthProviderDTO) {
        try{
            System.out.println(healthProviderDTO);
            healthProviderService.register(healthProviderDTO);
            return new ResponseCrudDTO<Void>("Saved HealthProvider Successfully!", 201,null);
        } catch (ValidationException v) {
            return new ResponseCrudDTO<Void>("Failed to Save!\n Reason: " + v.getMessage(), 403,null);
        } catch (WrongCredentials w) {
            return new ResponseCrudDTO<Void>("Failed to Save!\n Reason: " + w.getMessage(), 401,null);
        }
    }

    @GetMapping("/")
    public ResponseCrudDTO<List<HealthProvider>> findAll() {
        List<HealthProvider> healthProviders = healthProviderService.findAll();
        return new ResponseCrudDTO<>("Fetched HealthProviders Successfully!", 200, healthProviders);
    }
}
