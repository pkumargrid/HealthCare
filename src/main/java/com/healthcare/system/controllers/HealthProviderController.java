package com.healthcare.system.controllers;

import com.healthcare.system.dto.HealthProviderDTO;
import com.healthcare.system.dto.ResponseCrudDTO;
import com.healthcare.system.entities.HealthProvider;
import com.healthcare.system.exceptions.ValidationException;
import com.healthcare.system.exceptions.WrongCredentials;
import com.healthcare.system.services.HealthProviderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/healthcare")
@Tag(name = "healthCareProviderController", description = "Crud Operations for HealthCare Providers")
public class HealthProviderController {
    private final HealthProviderService healthProviderService;

    public HealthProviderController(HealthProviderService healthProviderService) {
        this.healthProviderService = healthProviderService;
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('healthProvider')")
    @Operation(summary = "Save a HealthProvider", description = "Takes HealthProviderDTO to register the doctor")
    public ResponseEntity<String> register(@RequestBody HealthProviderDTO healthProviderDTO) {
        try{
            healthProviderService.register(healthProviderDTO);
            return new ResponseEntity<>("Saved HealthProvider successfully!", HttpStatus.CREATED);
        } catch (ValidationException v) {
            return new ResponseEntity<>(v.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (WrongCredentials w) {
            return new ResponseEntity<>(w.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/")
    @Operation(summary = "FindAll HealthProviders", description = "Finds all the healthProviders in the health care system")
    public ResponseCrudDTO<List<HealthProvider>> findAll() {
        List<HealthProvider> healthProviders = healthProviderService.findAll();
        return new ResponseCrudDTO<>("Fetched HealthProviders Successfully!", 200, healthProviders);
    }
}
