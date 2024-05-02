package com.healthcare.system.controllers;

import com.healthcare.system.dto.HealthProviderDTO;
import com.healthcare.system.dto.ResponseCrudDTO;
import com.healthcare.system.entities.HealthProvider;
import com.healthcare.system.exceptions.ValidationException;
import com.healthcare.system.exceptions.WrongCredentials;
import com.healthcare.system.services.HealthProviderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseCrudDTO<List<HealthProvider>> findAll() {
        List<HealthProvider> healthProviders = healthProviderService.findAll();
        return new ResponseCrudDTO<>("Fetched HealthProviders Successfully!", 200, healthProviders);
    }
}
