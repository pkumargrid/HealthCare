package com.healthcare.system.controllers;

import com.healthcare.system.dto.PatientDTO;
import com.healthcare.system.dto.ResponseCrudDTO;
import com.healthcare.system.entities.Patient;
import com.healthcare.system.exceptions.ValidationException;
import com.healthcare.system.exceptions.WrongCredentials;
import com.healthcare.system.services.PatientService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/patients")
@Tag(name = "patientController", description = "Crud Operations for Patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('healthProvider')")
    @Operation(summary = "Save a Nurse", description = "Takes PatientDTO to register the patient")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = String.class)) }),
            @ApiResponse(responseCode = "400", description = "Incorrect credentials",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "400", description = "User not authorized",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = String.class))})})
    public ResponseEntity<String> save(PatientDTO patientDTO) {
        try {
            patientService.register(patientDTO);
            return new ResponseEntity<>("Saved Patient Successfully!", HttpStatus.CREATED);
        } catch (ValidationException v) {
            return new ResponseEntity<>(v.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (WrongCredentials w) {
            return new ResponseEntity<>(w.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/")
    @Operation(summary = "FindAll Patients", description = "Finds all the patients in the health care system")
    @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = Patient.class))) })
    public ResponseCrudDTO<List<Patient>> findAll() {
        List<Patient> patients = patientService.findAll();
        return new ResponseCrudDTO<>("Fetched Patients Successfully!", 200, patients);
    }
}
