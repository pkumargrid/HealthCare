package com.healthcare.system.controllers;

import com.healthcare.system.dto.PatientDTO;
import com.healthcare.system.dto.ResponseCrudDTO;
import com.healthcare.system.entities.Patient;
import com.healthcare.system.exceptions.ValidationException;
import com.healthcare.system.exceptions.WrongCredentials;
import com.healthcare.system.services.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping("/")
    public ResponseEntity<String> save(PatientDTO patientDTO) {
        try{
            patientService.register(patientDTO);
            return new ResponseEntity<>("Saved Patient Successfully!", HttpStatus.CREATED);
        } catch (ValidationException v) {
            return new ResponseEntity<>(v.getMessage(),HttpStatus.BAD_REQUEST);
        } catch (WrongCredentials w) {
            return new ResponseEntity<>(w.getMessage(),HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/")
    public ResponseCrudDTO<List<Patient>> findAll() {
        List<Patient> patients = patientService.findAll();
        return new ResponseCrudDTO<>("Fetched Patients Successfully!", 200, patients);
    }
}
