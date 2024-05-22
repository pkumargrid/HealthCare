package com.healthcare.system.controllers;

import com.healthcare.system.dto.HealthProviderDTO;
import com.healthcare.system.dto.ResponseCrudDTO;
import com.healthcare.system.entities.HealthProvider;
import com.healthcare.system.exceptions.ValidationException;
import com.healthcare.system.exceptions.WrongCredentials;
import com.healthcare.system.services.HealthProviderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = String.class)) }),
            @ApiResponse(responseCode = "400", description = "Incorrect credentials",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "400", description = "User not authorized",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = String.class))})})
    public ResponseEntity<String> register(@RequestBody HealthProviderDTO healthProviderDTO) {
        try {
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
    @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = HealthProvider.class))) })
    public ResponseCrudDTO<List<HealthProvider>> findAll() {
        List<HealthProvider> healthProviders = healthProviderService.findAll();
        return new ResponseCrudDTO<>("Fetched HealthProviders Successfully!", 200, healthProviders);
    }
}
