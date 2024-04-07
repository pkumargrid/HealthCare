package com.healthcare.system.controllers;

import com.healthcare.system.controllers.dto.ResponseSave;
import com.healthcare.system.entities.Doctor;
import com.healthcare.system.exceptions.ValidationException;
import com.healthcare.system.services.DoctorService;

public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    public ResponseSave save(Doctor doctor) {
        try{
            doctorService.save(doctor);
            return new ResponseSave("Saved Doctor Successfully!");
        } catch (ValidationException v) {
            return new ResponseSave("Failed to Save!\n Reason: " + v.getMessage());
        }
    }

}
