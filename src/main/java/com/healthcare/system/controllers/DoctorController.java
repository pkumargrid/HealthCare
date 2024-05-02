package com.healthcare.system.controllers;

import com.healthcare.system.dto.DoctorDTO;
import com.healthcare.system.dto.ResponseCrudDTO;
import com.healthcare.system.entities.Doctor;
import com.healthcare.system.exceptions.ValidationException;
import com.healthcare.system.exceptions.WrongCredentials;
import com.healthcare.system.services.DoctorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('healthProvider')")
    public ResponseEntity<String> register(@RequestBody DoctorDTO doctorDTO) {
        try{
            doctorService.register(doctorDTO);
            return new ResponseEntity<>("Saved Doctor successfully!", HttpStatus.CREATED);
        } catch (ValidationException v) {
            return new ResponseEntity<>(v.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (WrongCredentials w) {
            return new ResponseEntity<>(w.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/")
    public ResponseCrudDTO<List<Doctor>> findAll() {
        List<Doctor> doctors = doctorService.findAll();
        return new ResponseCrudDTO<>("Fetched Doctors Successfully!", 200, doctors);
    }


}
