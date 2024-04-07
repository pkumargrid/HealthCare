package com.healthcare.system.controllers;

import com.healthcare.system.controllers.dto.DoctorDTO;
import com.healthcare.system.controllers.dto.ResponseCrudDTO;
import com.healthcare.system.exceptions.ValidationException;
import com.healthcare.system.services.DoctorService;

public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    public ResponseCrudDTO register(DoctorDTO doctorDTO) {
        try{
            doctorService.register(doctorDTO);
            return new ResponseCrudDTO("Saved Doctor Successfully!");
        } catch (ValidationException v) {
            return new ResponseCrudDTO("Failed to Save!\n Reason: " + v.getMessage());
        }
    }

}
