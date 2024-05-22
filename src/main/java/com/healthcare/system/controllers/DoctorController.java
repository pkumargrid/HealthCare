package com.healthcare.system.controllers;

import com.healthcare.system.dto.DoctorDTO;
import com.healthcare.system.dto.ResponseCrudDTO;
import com.healthcare.system.entities.Doctor;
import com.healthcare.system.exceptions.ValidationException;
import com.healthcare.system.exceptions.WrongCredentials;
import com.healthcare.system.services.DoctorService;
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
@RequestMapping("/api/v1/doctors")
@Tag(name = "doctorController", description = "Crud Operations for Doctors")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('healthProvider')")
    @Operation(summary = "Save a Doctor", description = "Takes DoctorDTO to register the doctor")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = String.class)) }),
            @ApiResponse(responseCode = "400", description = "Incorrect credentials",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "400", description = "User not authorized",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = String.class))})})
    public ResponseEntity<String> register(@RequestBody DoctorDTO doctorDTO) {
        try {
            doctorService.register(doctorDTO);
            return new ResponseEntity<>("Saved Doctor successfully!", HttpStatus.CREATED);
        } catch (ValidationException v) {
            return new ResponseEntity<>(v.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (WrongCredentials w) {
            return new ResponseEntity<>(w.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/")
    @Operation(summary = "FindAll Doctors", description = "Finds all the doctors in the health care system")
    @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = Doctor.class))) })
    public ResponseCrudDTO<List<Doctor>> findAll() {
        List<Doctor> doctors = doctorService.findAll();
        return new ResponseCrudDTO<>("Fetched Doctors Successfully!", 200, doctors);
    }


}
